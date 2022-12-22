package com.project.owlback.codereview.service;

import java.util.List;  
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.owlback.codereview.dto.CodeHistoryGetDto;
import com.project.owlback.codereview.dto.CodeHistoryPostDto;
import com.project.owlback.codereview.dto.CodeReviewPostDto;
import com.project.owlback.codereview.model.CodeHistory;
import com.project.owlback.codereview.model.CodeReview;
import com.project.owlback.codereview.model.CodeHistoryTag;
import com.project.owlback.codereview.model.Tag;
import com.project.owlback.codereview.repository.CodeReviewHistoryRepository;
import com.project.owlback.codereview.repository.CodeReviewRepository;
import com.project.owlback.codereview.repository.CodeHistoryTagRepository;
import com.project.owlback.codereview.repository.TagRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.project.owlback.codereview.dto.CodeCommentResDto;
import com.project.owlback.codereview.dto.CodeReviewCommentReqDto;
import com.project.owlback.codereview.model.CodeComment;
import com.project.owlback.codereview.model.CodeCommentLike;
import com.project.owlback.codereview.model.CodeHistory;
import com.project.owlback.codereview.model.User;
import com.project.owlback.codereview.condition.CodeReviewSearchCondition;
import com.project.owlback.codereview.dto.CodeCommentDetailDto;
import com.project.owlback.codereview.dto.CodeHistoryDetailDto;
import com.project.owlback.codereview.dto.CodeHistoryResDto;
import com.project.owlback.codereview.dto.CodeReviewResDto;
import com.project.owlback.codereview.model.CodeComment;
import com.project.owlback.codereview.model.CodeHistory;
import com.project.owlback.codereview.model.CodeHistoryTag;
import com.project.owlback.codereview.model.CodeReview;
import com.project.owlback.codereview.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import java.sql.SQLException;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CodeReviewServiceImpl implements CodeReviewService {
	
	
	
	final private CodeHistoryTagRepository codeHistoryTagRepository;
	
	final private TagRepository tagRepository;
//	final private CodeReviewHistoryRepository codeReviewHistoryRepository;
	
    private final CodeReviewRepositoryCustom codeReviewRepositoryCustom;

    private final CodeReviewHistoryRepository codeReviewHistoryRepository;

    private final CodeHistoryTagRepositoryCustom codeHistoryTagRepositoryCustom;

    private final CodeCommentLikeRepository codeCommentLikeRepository;

    private final CodeCommentRepositoryCustom codeCommentRepositoryCustom;

    private final CodeHistoryRepositoryCustom codeHistoryRepositoryCustom;


    private final CodeReviewRepository codeReviewRepository;
    private final UserRepository userRepository;
    private final CodeHistoryRepository codeHistoryRepository;
    private final CodeCommentRepository codeCommentRepository;

    private final CodeCommentCustomRepository codeCommentCustomRepository;
    
    @Override
    @Transactional
	public Long create(CodeReviewPostDto codeReviewPostDto) {
    	System.out.println("!!!!!!!");
    	
		log.info("info log={}", codeReviewPostDto);
		
		// user 정보
		// codereview 정보
		CodeReview codeReview = CodeReview.builder()
				.versionCount(1)
				.title(codeReviewPostDto.getTitle())
				.writer(codeReviewPostDto.getWriter())
				.studyGroup(codeReviewPostDto.getStudyGroup())
				.codeScope(codeReviewPostDto.getCodeScope())
				.codeLanguage(codeReviewPostDto.getCodeLanguage())
				.build();
		log.info("info log={}", codeReview);
		
		codeReviewRepository.save(codeReview);
		
		log.info("info log={}", codeReview);
		
		CodeHistory codeHistory = CodeHistory.builder()
				.codeReview(codeReview)
				.code(codeReviewPostDto.getCodeHistoryPostDto().getCode())
				.subTitle(codeReviewPostDto.getCodeHistoryPostDto().getSubTitle())
				.contents(codeReviewPostDto.getCodeHistoryPostDto().getContents())
				.versionNum(codeReviewPostDto.getCodeHistoryPostDto().getVersionNum())
				.like(0)
				.commentCount(0)
				.build();
		log.info("info log={}",codeHistory);
		
		createHistory(codeHistory,codeReviewPostDto.getCodeHistoryPostDto().getTag());
		
		log.info("info log={}",codeHistory);
		
		
		log.info("info log={}", codeReviewPostDto);
		
		return codeReview.getId();
	}
	
    @Override
	@Transactional
	public void createCodeHistoryTag(CodeHistory codeHistory, List<Tag> tag) {
		
		log.info("info log={}", tag);
		tag.stream()
		.forEach(x->codeHistoryTagRepository.save(CodeHistoryTag.builder()
				.count(0)
				.codeHistory(codeHistory)
				.tag(x)
				.build()));
		log.info("info log={}", tag);
	}
	
	@Override
	@Transactional
	public List<Tag> createTag(List<Tag> tag) {
		
		log.info("info1 log={}",tag);
		for (int i = 0; i < tag.size(); i++) {
			// createtag
			if(!tagRepository.existsByContent(tag.get(i).getContent())) {
				tag.get(i).setCount(0);
				tagRepository.save(tag.get(i));
			}else {
				tagRepository.CountUp(tag.get(i).getContent());
				tag.set(i,tagRepository.findByContent(tag.get(i).getContent()));
			}
		}
		return tag;
	}
	
	@Override
	@Transactional
	public Long createHistory(CodeHistory codeHistory,List<Tag> tag) {
		// TODO Auto-generated method stub
		log.info("info log={}", codeHistory);
		codeReviewHistoryRepository.save(codeHistory);
		if(tag != null) {
			tag = createTag(tag);
			
			createCodeHistoryTag(codeHistory, tag);
		}
		return codeHistory.getId();
	}
	@Override
	@Transactional
	public CodeHistory setCodeReviewToCodeHistory(CodeHistoryPostDto codeHistoryPostDto, Long id) throws Exception{
		System.out.println(id+" "+codeHistoryPostDto.toString());
		System.out.println(codeReviewRepository.findById(id).orElseThrow());
		CodeHistory codeHistory = CodeHistory.builder()
				.codeReview(codeReviewRepository.findById(id).orElseThrow())
				.code(codeHistoryPostDto.getCode())
				.subTitle(codeHistoryPostDto.getSubTitle())
				.contents(codeHistoryPostDto.getContents())
				.versionNum(codeHistoryPostDto.getVersionNum())
				.like(0)
				.commentCount(0)
				.build();
		return codeHistory;
	}
	@Override
	@Transactional
	public List<CodeHistoryGetDto> getCodeReviewHistory(Long id) throws Exception{
//		CodeReview optionCodeReview = codeReviewRepository.findById(id);
//		===> []
		
		CodeReview optionCodeReview = codeReviewRepository.findById(id).orElseThrow();
//		===> NoSuchElementException
		log.info("info log={}", optionCodeReview);
		
		List<CodeHistoryGetDto> codeHistoryGetDto = codeReviewHistoryRepository.findByCodeReview(optionCodeReview).stream()
				.map(CodeHistoryGetDto::fromEntity).collect(Collectors.toList());
		
		for (int i = 0; i < codeHistoryGetDto.size(); i++) {
			CodeHistoryGetDto temp = codeHistoryGetDto.get(i);
			log.info("info log={}", temp);
			List<CodeHistoryTag> tempTag = codeHistoryTagRepository.findByCodeHistory(CodeHistory.builder().id(temp.getId()).build());
			log.info("info log={}", tempTag);
			codeHistoryGetDto.get(i).setTag((List<Tag>) tempTag.stream()
					.map(x->x.getTag()).collect(Collectors.toList()));
		}
		return codeHistoryGetDto;
	}
	
    @Override
    public Page<?> codeReviewSearch(String key, String word, Pageable pageable) throws Exception {

        CodeReviewSearchCondition condition = new CodeReviewSearchCondition();
        condition.setKey(key);

        switch (key) {
            case "study" -> condition.setStudyGroupId(Long.parseLong(word));
            case "title" -> condition.setTitle(word.toUpperCase());
            case "writer" -> condition.setWriter(word.toUpperCase());
            case "language" -> condition.setLanguage(word.toUpperCase());
            case "tag" -> condition.setTag(word.toUpperCase());
        }


        if (!key.equals("tag")) {
            Page<CodeReview> list = null;

            list = codeReviewRepositoryCustom.SearchByCondition(condition, pageable);
            log.info("get codeReviewList : {}", list);

            return changeToCodeReviewDto(list);
        } else {
            Page<CodeHistoryTag> tagList = codeHistoryTagRepositoryCustom.findByTagContent(condition, pageable);
            log.info("get codeReviewTagList : {}", tagList);

            Page<CodeHistory> list = null;

            list = tagList.map(CodeHistoryTag::getCodeHistory);

            return changeToCodeHistoryDto(list);
        }

    }

    @Override
    public CodeHistoryDetailDto codeReviewHistoryDetail(
            int codeReviewId, int versionNum, long userId, Pageable pageable) throws Exception {
        CodeHistory history = codeReviewHistoryRepository.findByCodeReviewIdAndVersionNum(codeReviewId, versionNum).orElseThrow();
        log.info("found history : {}", history);
        CodeHistoryDetailDto result = changeToCodeReviewHistoryDto(history, userId, pageable);
        log.info("change to dto : {}", result);

        return result;
    }

    @Override
    public Page<CodeCommentDetailDto> codeReviewCommentsDetail(
            long historyId, int startLine, long userId, Pageable pageable) throws Exception {

        CodeReviewSearchCondition condition = new CodeReviewSearchCondition();
        condition.setCodeHistoryId(historyId);
        condition.setStartLine(startLine);

        Page<CodeComment> list = codeCommentRepositoryCustom.getCodeComment(condition, pageable);
        log.info("get code comments : {}", list);

        Page<CodeCommentDetailDto> comments = changeToCodeCommentDto(list, userId);
        log.info("change to dto : {}", comments);

        return comments;
    }

    @Override
    public Page<CodeCommentDetailDto> codeReviewComments(long historyId, long userId, Pageable pageable) throws Exception {

        CodeReviewSearchCondition condition = new CodeReviewSearchCondition();
        condition.setCodeHistoryId(historyId);

        Page<CodeComment> list = codeCommentRepositoryCustom.getCodeComment(condition, pageable);
        log.info("get code comments : {}", list);

        Page<CodeCommentDetailDto> comments = changeToCodeCommentDto(list, userId);
        log.info("change to dto : {}", comments);

        return comments;
    }

    @Override
    public List<String> getRelativeTags(String word) throws Exception {
        CodeReviewSearchCondition condition = new CodeReviewSearchCondition();
        condition.setTag(word);

        List<String> tags = codeHistoryTagRepositoryCustom.getTagContent(condition);
        log.info("get tags : {}", tags);

        return tags;
    }


    // CodeReviewList -> CodeReviewDtoList
    public Page<CodeReviewResDto> changeToCodeReviewDto(Page<CodeReview> list) throws Exception {
        Page<CodeReviewResDto> dtoList = list.map(c -> {
            try {
                CodeReviewSearchCondition condition = new CodeReviewSearchCondition();
                condition.setCodeReviewId(c.getId());

                List<String> tags = codeHistoryTagRepositoryCustom.getTagContentbyCodeReviewId(c.getId(), c.getVersionCount());
                log.info("get tagList : {}", tags);

                String subtitle = codeHistoryRepositoryCustom.getSubTitle(c.getId(), c.getVersionCount());

                return new CodeReviewResDto(c, subtitle, tags);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        log.info("change to dto successfully");
        return dtoList;
    }

    private Page<CodeHistoryResDto> changeToCodeHistoryDto(Page<CodeHistory> list) {
        Page<CodeHistoryResDto> dtoList = list.map(h -> {
            try {
                List<String> tags = codeHistoryTagRepositoryCustom.getTagContentbyCodeHistoryId(h.getId());
                log.info("get tagList : {}", tags);

                return new CodeHistoryResDto(h, tags);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        log.info("change to dto successfully");
        return dtoList;

    }

    public CodeHistoryDetailDto changeToCodeReviewHistoryDto(
            CodeHistory history, long userId, Pageable pageable) throws Exception {
        CodeReviewSearchCondition condition = new CodeReviewSearchCondition();
        condition.setCodeHistoryId(history.getId());

        Page<CodeComment> list = codeCommentRepositoryCustom.getCodeComment(condition, pageable);
        Page<CodeCommentDetailDto> comments = changeToCodeCommentDto(list, userId);
        log.info("get code comments : {}", comments);

        List<String> tags = codeHistoryTagRepositoryCustom.getTagContentbyCodeHistoryId(history.getId());

        return new CodeHistoryDetailDto(history, tags, comments);
    }

    public Page<CodeCommentDetailDto> changeToCodeCommentDto(Page<CodeComment> comments, long userId) throws Exception {

        return comments.map(c -> {
            try {
                int cnt = codeCommentLikeRepository.countByUserIdAndCodeCommentId(userId, c.getId());
                boolean isLike = (cnt > 0);
                log.info("get code comment like : {}", isLike);

                return new CodeCommentDetailDto(c, isLike);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    @Transactional
    public Long addComment(CodeReviewCommentReqDto reqDto) {
        Long userId = 1L; // temporary use before create user function

        final CodeComment comment = CodeComment.builder()
                .startLine(reqDto.getStartLine())
                .endLine(reqDto.getEndLine())
                .parent(reqDto.getParent())
                .depth(reqDto.getDepth())
                .contents(reqDto.getContents())
                .likeCount(0)
                .build();

        // 1. JpaAuditing
        // 2. builder 패턴
        // 3. Optional
        // 4. logging -> slf4j -> 공부하면 좋다
        final User user = userRepository.findById(userId).orElseThrow();
        final CodeHistory codeHistory = codeHistoryRepository.findById(reqDto.getCodeHistoryId()).orElseThrow();

        log.info("found user : {}", user);
        log.info("found codeHistory : {}", codeHistory);
        comment.setCodeHistory(codeHistory);
        comment.setWriter(user);

        log.info("comment : {}", comment);

        codeCommentRepository.save(comment);
        log.info("comment has been saved successfully");

        return comment.getId();
    }

    @Override
    @Transactional
    public int likeComment(CodeReviewCommentReqDto reqDto) {
        Long userId = 1L; // temporary use before create user function

        final CodeComment comment = codeCommentRepository.findById(reqDto.getCodeCommentId()).orElseThrow();
        final User user = userRepository.findById(userId).orElseThrow();

        log.info("comment : {}, user : {}", comment, user);

        final Optional<CodeCommentLike> byCodeCommentAndUser =
                codeCommentLikeRepository.findByCodeCommentAndUser(comment, user);

        byCodeCommentAndUser.ifPresentOrElse(
                codeCommentLike -> {
                    codeCommentLikeRepository.delete(codeCommentLike);
                    comment.dislike();
                },
                () -> {
                    final CodeCommentLike codeCommentLike = CodeCommentLike.builder().codeComment(comment).user(user).build();
                    codeCommentLikeRepository.save(codeCommentLike);
                    comment.like();
                });

        return comment.getLikeCount();
    }

    @Override
    public Page<CodeCommentResDto> getMyComments(String key, String word, Pageable pageable) {

        final Long uid = 1L; // temporary comment writer's
        final User user = userRepository.findById(uid).get();

        Page<CodeCommentResDto> res = null;// id
        Page<CodeComment> page;


        //query dsl 적용전
//      if(key.equals("title")) {
//            // find by code review title
//            page = codeCommentRepository.findByWriterAndCodeReviewTitle(user, word, pageable);
//            res = page.map(CodeCommentResDto::new);
//        } else if(key.equals("contents")) {
//            // find by code comment contents
//            page = codeCommentRepository.findByWriterAndContentsContains(user, word, pageable);
//            res = page.map(CodeCommentResDto::new);
//        } else if(key.equals("writer")) {
//            // find by code review writer
//            page = codeCommentRepository.findByWriterAndUserNickName(user, word, pageable);
//            res = page.map(CodeCommentResDto::new);
//        } else {
//            throw new IllegalArgumentException();
//        }

        // queryDsl -> 동적 쿼리 작성에 필수로 사용되는 오픈소스
        page = codeCommentCustomRepository.getMyComments(user, key, word, pageable);
        res = page.map(CodeCommentResDto::new);

        return res;
    }
}
