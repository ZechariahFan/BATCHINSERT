import com.dj.mapper.TbShopMapper;
import com.dj.model.TbShop;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fzq15 on 2019/9/8.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-mapper.xml")
public class Test1 {


    @Autowired
    private TbShopMapper tbShopMapper;

    @Test
    public void testInsert(){
        List<TbShop> newList = new ArrayList<TbShop>();
        for (int j=0;j<1000000;j++){
            BigDecimal a = new BigDecimal(j);
            TbShop t1=new TbShop();
            t1.setId(a);
            newList.add(t1);
        }
    List<TbShop> tt1 =null;
        int listSize = newList.size();//数据大小
        int count  = 999; //初始化一个变量,默认1000
        //根据变量确定一次处理多少条数据
        if( listSize >= 1 && listSize < 1000) {
            count = 333;
        }else if(listSize >= 1000 && listSize < 10000) {
            count = 1333;
        }else if(listSize >= 10000) {
            count = 20000;
        }
        System.out.println(listSize);
        int loopSize  = (listSize / count) + 1;//循环多少次
        for (int i = 0; i <loopSize;i++) {
            if((i + 1) == loopSize){
                int start  = (i * count);
                int end = listSize ;//总数
                tt1  = newList.subList( start, end);//返回一个List集合的其中一部分视图。包含start，不包含end
            } else {
                int start  = (i * count);
                int end = ((i + 1) * count);
                tt1  = newList.subList( start, end);
            }
            tbShopMapper.insert2(tt1);
            //configService.insertData(newList);//调用service里面的方法
        }
    }

    @Test
    public void test2(){
        long startTime=System.currentTimeMillis();

        Connection conn=null;
        PreparedStatement stmt=null;
        try{
            List<TbShop> newList = new ArrayList<TbShop>();
            for (int j=0;j<1000000;j++){
                BigDecimal a = new BigDecimal(j);
                TbShop t1=new TbShop();
                t1.setId(a);
                newList.add(t1);
            }
            conn=JdbcUtils.getConnection();
            conn.setAutoCommit(false);

            stmt=conn.prepareStatement("INSERT INTO tb_shop VALUES (?)");
            System.out.println("数据大小："+newList.size()); //1000000

            int num=0;
            for(TbShop v:newList){
                num++;
                stmt.setBigDecimal(1, v.getId());
                stmt.addBatch();
//注意: 每5万，提交一次;这里不能一次提交过多的数据,我测试了一下，6万5000是极限，6万6000就会出问题，插入的数据量不对。
                if(num>50000){
                    stmt.executeBatch();
                    conn.commit();
                    num=0;
                }
            }
            stmt.executeBatch();
            conn.commit();
        }catch(Exception e){
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }finally{
            JdbcUtils.releaseResources(conn, stmt);
            long endTime=System.currentTimeMillis();
            System.out.println("方法执行时间："+(endTime-startTime)+"ms");
        }
    }
}
