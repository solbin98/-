package com.project.file;

import com.project.dao.BoardFileDao;
import com.project.dao.FileDao;
import com.project.dao.ProblemFileDao;
import com.project.dao.ProfileFileDao;
import com.project.dto.BoardFileDto;
import com.project.dto.FileDto;
import com.project.dto.ProblemFileDto;
import com.project.dto.ProfileFileDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class FileService {
    @Autowired
    MessageSource messageSource;
    @Autowired
    FileDao fileDao;
    @Autowired
    BoardFileDao boardFileDao;
    @Autowired
    ProblemFileDao problemFileDao;
    @Autowired
    ProfileFileDao profileFileDao;

    public void setUsedColumnTrueByIdList(List<Integer> ids) throws Exception {
        if(ids == null) return;
        for(int i=0;i<ids.size();i++) fileDao.setUsedColumnTrueByFileId(ids.get(i));
    }

    public void setUsedColumnFalseByIdList(List<Integer> ids) throws Exception {
        if(ids == null) return;
        for(int i=0;i<ids.size();i++) fileDao.setUsedColumnFalseByFileId(ids.get(i));
    }

    public List<Integer> getFileIdListFromBoardFileDtoList(List<BoardFileDto> list){
        if(list == null) return new ArrayList<Integer>();
        List<Integer> idList = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            idList.add(list.get(i).getFile_id());
        }
        return idList;
    }

    public List<BoardFileDto> getBoardFileDtoByBoardId(int board_id){
        return boardFileDao.selectByBoardId(board_id);
    }

    public int getLastFileId(){
        return fileDao.selectLastFileId();
    }

    public void addFile(FileDto fileDto) throws Exception {
        fileDao.insert(fileDto);
    }

    public void addBoardFileByFileIdListAndBoardId(List<Integer> list, int board_id){
        if(list == null) return;
        for(int i=0;i<list.size();i++) {
            try{ addBoardFile(new BoardFileDto(board_id, list.get(i))); }
            catch (Exception e){ System.out.println("중복 존재!"); }
        }
    }

    public void addProfileFileByFileIdListAndBoardId(List<Integer> list, int board_id){
        if(list == null) return;
        for(int i=0;i<list.size();i++) {
            try{ addProfileFile(new ProfileFileDto(board_id, list.get(i))); }
            catch (Exception e){ System.out.println("중복 존재!"); }
        }
    }

    public void addProblemFileByFileIdListAndBoardId(List<Integer> list, int board_id){
        if(list == null) return;
        for(int i=0;i<list.size();i++) {
            try{ addProblemFile(new ProblemFileDto(board_id, list.get(i))); }
            catch (Exception e){ System.out.println("중복 존재!"); }
        }
    }

    public void addBoardFile(BoardFileDto boardFileDto) throws Exception{
        boardFileDao.insert(boardFileDto);
    }

    public void addProfileFile(ProfileFileDto profileFileDto) throws Exception{
        profileFileDao.insert(profileFileDto);
    }

    public void addProblemFile(ProblemFileDto problemFileDto) throws Exception{
        problemFileDao.insert(problemFileDto);
    }
}
