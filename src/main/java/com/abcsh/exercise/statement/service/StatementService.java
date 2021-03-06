package com.abcsh.exercise.statement.service;

import com.abcsh.exercise.statement.entity.PagingResult;
import com.abcsh.exercise.statement.entity.Result;
import com.abcsh.exercise.statement.entity.Statement;
import com.abcsh.exercise.statement.entity.User;
import com.abcsh.exercise.statement.mapper.StatementMapper;
import com.abcsh.exercise.statement.util.Paging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @program: Statement
 * @description: CRUD Option
 * @author: 乘梦碧溪
 * @create: 2020-07-03 09:14
 **/
@Service
public class StatementService {
    @Autowired
    StatementMapper statementMapper;
    @Autowired
    HttpServletRequest request;
//查询所有
    public PagingResult retrieveAllStatementList(int currentPage,int pageSize){
        List<?> list = statementMapper.retrieveAllStatementList(Paging.toStart(currentPage,pageSize),pageSize);
        return new PagingResult<>(
                ((List<Integer>)list.get(1)).get(0)
                ,(List<Statement>)list.get(0)
        );
    }
//起止时间查询
    public PagingResult retrieveStatementListByRangeDate(String beginDate,String overDate,int currentPage,int pageSize){
        List<?> list = statementMapper.retrieveStatementListByRangeDate( beginDate, overDate, Paging.toStart(currentPage,pageSize),pageSize);
        System.out.println("***************retrieveStatementListByRangeDate*******************" + new Date());
        return new PagingResult<>(
                ((List<Integer>)list.get(1)).get(0)
                ,(List<Statement>)list.get(0)
        );
    }
//单日时间查询
    public PagingResult retrieveStatementListBySingleDate(String singleDate,int currentPage,int pageSize){
        List<?> list = statementMapper.retrieveStatementListBySingleDate(singleDate,Paging.toStart(currentPage,pageSize),pageSize);
        System.out.println("***************retrieveStatementListBySingleDate*******************" + new Date());
        return new PagingResult<>(
                ((List<Integer>)list.get(1)).get(0)
                ,(List<Statement>)list.get(0)
        );
    }

//按id查询返回Statement
    public Statement retrieveStatementById(int id){
        return statementMapper.retrieveStatementById(id);
    }

//新增
    public Result createStatement(Statement statement) {
      try {
          User user = (User) request.getSession().getAttribute("user");
          Date date = new Date();
          SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
          String date_Str = f.format(date);

          statement.setTimeStamp(date_Str);
          statement.setReporter(user.getAccount());
          System.out.println(">>>>>>>>>>>>>statement:"+statement);
          statementMapper.createStatement(statement);
          System.out.println("++++++++++++");
      }catch (Exception e) {
          return new Result(false,e.getMessage());
      }
      return new Result(true,"");
    }

//删除
   public Result deleteStatementById(int id) {
       // return statementMapper.deleteStatementById(id);
       if (statementMapper.deleteStatementById(id))
            return new Result(true,"");
       else
           return new Result(false,"删除错误！");
    }

//更新
    public Result updateStatementById(Statement statement) {

            //Statement statement1 = this.retrieveStatementById(id);
            //statement.setReporter(statement1.getReporter());
            //statement.setTimeStamp(statement.getTimeStamp());

        System.out.println("~~~~~~~~~~~~更新操作已执行完毕！");
        boolean flag = statementMapper.updateStatementById(statement);
        if (flag)
            return new Result(true,"");
        else
            return new Result(false,"更新出错！");
    }


}
