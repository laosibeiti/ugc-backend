/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 19.3.1
  Time: 21:49
  Info:
*/

package top.justdj.ugc.service.impl;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apdplat.word.WordSegmenter;
import org.apdplat.word.segmentation.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import top.justdj.common.entity.JobInfo;
import top.justdj.common.entity.pagefilter.CompanyJobPageFilter;
import top.justdj.common.entity.pagefilter.JobPageFilter;
import top.justdj.ugc.config.repository.JobInfoRepository;
import top.justdj.ugc.dao.JobInfoDAO;
import top.justdj.ugc.service.JobInfoService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 *
 * @author justdj
 * @email top90982@gmail.com
 * Date: 19.3.1
 * Time: 21:49&
 */
@Slf4j
@Service
public class JobInfoServiceImpl extends CommonServiceImpl<JobInfoRepository,JobInfo>
implements JobInfoService{
	
	
	@Autowired
	private JobInfoRepository jobInfoRepository;
	
	@Autowired
	private JobInfoDAO jobInfoDAO;
	
	
	@Override
	public Page<JobInfo> pageFind(CompanyJobPageFilter filter) {
		Query query =  new Query();
		PageRequest pageRequest = PageRequest.of(filter.getPageNum() ,filter.getPageSize(), Sort.by(Sort.Order.asc("createTime")));
		query.with(pageRequest);
		if (!StringUtils.isEmpty(filter.getContactPerson())){
			Pattern name = Pattern.compile("^.*"+filter.getContactPerson() +".*$", Pattern.CASE_INSENSITIVE);
			query.addCriteria(Criteria.where("contactPerson").regex(name));
		}
		if (!StringUtils.isEmpty(filter.getJobName())){
			Pattern name = Pattern.compile("^.*"+filter.getJobName() +".*$", Pattern.CASE_INSENSITIVE);
			query.addCriteria(Criteria.where("jobName").regex(name));
		}
		if (!StringUtils.isEmpty(filter.getPayMethod())){
			query.addCriteria(Criteria.where("payMethod").is(filter.getPayMethod()));
		}
		if (!CollectionUtils.isEmpty(filter.getJobType())){
			query.addCriteria(Criteria.where("jobType").is(filter.getJobType()));
		}
		if (!StringUtils.isEmpty(filter.getCompanyId())){
			query.addCriteria(Criteria.where("companyId").is(filter.getCompanyId()));
		}
		if (filter.getIsAdmin()){
			query.addCriteria(Criteria.where("enableStatus").ne("3"));
		}
		return new PageImpl<JobInfo>(jobInfoDAO.query(query),pageRequest,jobInfoDAO.count(query));
	}
	
	
	@Override
	public Page <JobInfo> pageFind(JobPageFilter filter) {
		Query query =  new Query();
		PageRequest pageRequest ;
		//2是正常状态的兼职
		query.addCriteria(Criteria.where("enableStatus").is("2"));
		//兼职名称
		// TODO: 19.3.8 分词
		if (!StringUtils.isEmpty(filter.getJobName())){
			List<String>  list = new ArrayList <>();
			list.add(filter.getJobName());
			log.info("准备运行分词");
			List<Word> wordList = WordSegmenter.seg(filter.getJobName());
			List<String> temp = wordList.stream().map(Word::getText).collect(Collectors.toList());
			list.addAll(temp);
			List<Pattern> patternList = new ArrayList <>();
			list.forEach(a ->{
				patternList.add( Pattern.compile("^.*"+a +".*$", Pattern.CASE_INSENSITIVE));
			});
			log.info("分词数组 {}", JSON.toJSONString(list));
			switch (patternList.size()){
				case 3:
					//两个关键词
					query.addCriteria(new Criteria().orOperator(
							Criteria.where("jobName").regex(patternList.get(0)),
							new Criteria().andOperator(
								Criteria.where("jobName").regex(patternList.get(1)),
								Criteria.where("jobName").regex(patternList.get(2))
							)
					));
					break;
				case 4:
					//三个关键词
					query.addCriteria(new Criteria().orOperator(
							Criteria.where("jobName").regex(patternList.get(0)),
							new Criteria().andOperator(
									Criteria.where("jobName").regex(patternList.get(1)),
									Criteria.where("jobName").regex(patternList.get(2))
							),
							new Criteria().andOperator(
									Criteria.where("jobName").regex(patternList.get(1)),
									Criteria.where("jobName").regex(patternList.get(3))
							),
							new Criteria().andOperator(
									Criteria.where("jobName").regex(patternList.get(3)),
									Criteria.where("jobName").regex(patternList.get(2))
							)
					));
					break;
				case 5:
					//四个关键词
					query.addCriteria(new Criteria().orOperator(
							Criteria.where("jobName").regex(patternList.get(0)),
							new Criteria().andOperator(
									Criteria.where("jobName").regex(patternList.get(1)),
									Criteria.where("jobName").regex(patternList.get(2))
							),
							new Criteria().andOperator(
									Criteria.where("jobName").regex(patternList.get(1)),
									Criteria.where("jobName").regex(patternList.get(3))
							),
							new Criteria().andOperator(
									Criteria.where("jobName").regex(patternList.get(1)),
									Criteria.where("jobName").regex(patternList.get(4))
							),
							new Criteria().andOperator(
									Criteria.where("jobName").regex(patternList.get(2)),
									Criteria.where("jobName").regex(patternList.get(3))
							),
							new Criteria().andOperator(
									Criteria.where("jobName").regex(patternList.get(2)),
									Criteria.where("jobName").regex(patternList.get(4))
							),
							new Criteria().andOperator(
									Criteria.where("jobName").regex(patternList.get(3)),
									Criteria.where("jobName").regex(patternList.get(4))
							)
					));
					break;
				default:
					query.addCriteria(Criteria.where("jobName").regex(patternList.get(0)));
					break;
				}
		}
			
		
		//所属分类
		if (!CollectionUtils.isEmpty(filter.getJobType())){
			query.addCriteria(Criteria.where("jobType").is(filter.getJobType()));
		}
		//结算方式
		if (!StringUtils.isEmpty(filter.getPayMethod())){
			query.addCriteria(Criteria.where("payMethod").is(filter.getPayMethod()));
		}
		//性别要求
		List<Integer> genderOption = Arrays.asList(-1,1,2);
		if (genderOption.contains(filter.getGender())){
			query.addCriteria(Criteria.where("gender").is(filter.getGender()));
		}
		//学历要求
		if (!StringUtils.isEmpty(filter.getAcademicRequirements())){
			query.addCriteria(Criteria.where("academicRequirements").is(filter.getAcademicRequirements()));
		}
		// TODO: 19.3.8 子地区查询
		//地区
		
		//排序
		if ("default".equals(filter.getSort())){
			pageRequest = PageRequest.of(filter.getPageNum() ,filter.getPageSize(), Sort.by(Sort.Order.desc("popularScore")));
		}else if ("salary".equals(filter.getSort())){
			pageRequest = PageRequest.of(filter.getPageNum() ,filter.getPageSize(), Sort.by(Sort.Order.desc("salary")));
		}else {
				pageRequest = PageRequest.of(filter.getPageNum() ,filter.getPageSize(), Sort.by(Sort.Order.desc("createTime")));
		}
		query.with(pageRequest);
		
		return new PageImpl<JobInfo>(jobInfoDAO.query(query),pageRequest,jobInfoDAO.count(query));
	}
	
	@Override
	public List <JobInfo> findAllSignUpJob(String userId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("appliedUserId").all(userId));
		return jobInfoDAO.query(query);
	}
}
