package com.project.common;

import com.project.domain.file.FileDto;
import com.project.domain.file.FileService;
import com.project.domain.member.MemberDto;
import com.project.domain.member.MemberService;
import com.project.domain.member.profile.ProfileDto;
import com.project.domain.member.profile.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class Scheduler {
    @Autowired
    MemberService memberService;
    @Autowired
    ProfileService profileService;
    @Autowired
    FileService fileService;

    // 유저의 랭킹을 업데이트 하기 위함
    @Scheduled(fixedDelay = 1000 * 60 * 15 ) // 15분 간격으로 유저 랭크 업데이트 * 60 * 15
    public void updateUserRankingState() {
        List<MemberDto> allMemberList = memberService.getAllMember();
        for(int i=0;i<allMemberList.size();i++){
            int member_id = allMemberList.get(i).getMember_id();
            int solvedNumber = profileService.getSolvedProblemCountByMemberId(member_id);
            int submittedNumber = profileService.getSubmittedProblemCountByMemberId(member_id);
            profileService.update(new ProfileDto(member_id, submittedNumber, solvedNumber));
        }
    }

    // 매일 새벽 1시에 일괄 삭제 수행
    @Scheduled(cron = "0 0 3 * * ?")
    public void removeUnusedImageFile() {
        List<FileDto> fileList = fileService.getUnusedFile();
        for(int i=0;i<fileList.size();i++){
            String filePath = fileList.get(i).getPath();
            File file = new File(filePath);

            LocalDateTime nowTime = LocalDateTime.now();
            LocalDateTime oneDayAfter = fileList.get(i).getDate();
            oneDayAfter.plusDays(1);

            // 사용되지 않는 이미지 중에서 하루가 지난 이미지들만 삭제해야함
            // 게시글을 작성중인 사람이 존재할 수 있기 때문..
            if(nowTime.isAfter(oneDayAfter)){
                if(file.exists()){
                    if(file.delete()) System.out.println("파일 삭제 성공!");
                    else System.out.println("파일 삭제 실패");
                }
                fileService.removeFileById(fileList.get(i).getFile_id());
            }
        }
    }
}
