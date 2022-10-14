package com.project.domain.file;

import com.project.domain.board.common.dao.BoardFileDao;
import com.project.domain.board.common.dto.BoardFileQuestionDto;
import com.project.domain.problem.dao.ProblemFileDao;
import com.project.domain.board.common.dto.BoardFileDto;
import com.project.domain.problem.dto.ProblemFileDto;
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

    public List<Integer> getFileIdListFromBoardFileQuestionDtoList(List<BoardFileQuestionDto> list){
        if(list == null) return new ArrayList<Integer>();
        List<Integer> idList = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            idList.add(list.get(i).getFile_id());
        }
        return idList;
    }

    public List<BoardFileQuestionDto> getBoardFileDtoByQuestionId(int question_id){
        return boardFileDao.selectByQuestionIdWithJoin(question_id);
    }

    public List<FileDto> getUnusedFile(){
        return fileDao.selectUnused();
    }

    public void removeFileById(int file_id){
        fileDao.deleteById(file_id);
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

    public void addBoardFileByFileIdListAndBoardId(List<Integer> list, int board_id) throws Exception{
        if(list == null) return;
        for(int i=0;i<list.size();i++) {
            addBoardFile(new BoardFileDto(board_id, list.get(i)));
        }
    }

    public void addProblemFileByFileIdListAndBoardId(List<Integer> list, int board_id) throws Exception{
        if(list == null) return;
        for(int i=0;i<list.size();i++) {
            addProblemFile(new ProblemFileDto(board_id, list.get(i)));
        }
    }

    public void addBoardFile(BoardFileDto boardFileDto) throws Exception{
        boardFileDao.insert(boardFileDto);
    }

    public void addProblemFile(ProblemFileDto problemFileDto) throws Exception{
        problemFileDao.insert(problemFileDto);
    }
}
