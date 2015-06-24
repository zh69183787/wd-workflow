package test;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created with IntelliJ IDEA.
 * User: zhoushun
 * Date: 2015/2/6
 * Time: 9:58
 * To change this template use File | Settings | File Templates.
 */
public class TestWordDealUtil {
    // 测试 null 时的处理情况
    @Test
    public void wordFormat4DBNull() {
        String target = null;
        String result = WordDealUtil.wordFormat4DB(target);

        assertNull(result);
    }

    // 测试空字符串的处理情况
    @Test
    public void wordFormat4DBEmpty() {
        String target = "";
        String result = WordDealUtil.wordFormat4DB(target);

        assertEquals("", result);
    }

    // 测试当首字母大写时的情况
    @Test
    public void wordFormat4DBegin() {
        String target = "EmployeeInfo";
        String result = WordDealUtil.wordFormat4DB(target);

        assertEquals("employee_info", result);
    }

    // 测试当尾字母为大写时的情况
    @Test
    public void wordFormat4DBEnd() {
        String target = "employeeInfoA";
        String result = WordDealUtil.wordFormat4DB(target);

        assertEquals("employee_info_a", result);
    }

    // 测试多个相连字母大写时的情况
    @Test
    public void wordFormat4DBTogether() {
        String target = "employeeAInfo";
        String result = WordDealUtil.wordFormat4DB(target);

        assertEquals("employee_a_info", result);
    }

    // 测试 wordFormat4DB 正常运行的情况
    @Test
    public void wordFormat4DBNormal() {
        String target = "employeeInfoData";
        String result = WordDealUtil.wordFormat4DB(target);

        assertEquals("employee_info_data", result);
    }
}
