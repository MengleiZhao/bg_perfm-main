package com.common.system.util;

import com.common.business.planmgr.pre.mkoutline.entity.TResearchOutline;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Title: WordUtils
 * Description： 导出word使用工具类
 * Author: 陈睿超
 * Date: 2021/4/25 17:21
 * Updater: 陈睿超
 * Date: 2021/4/25 17:21
 * Company: 天职国际
 * Version:
 **/
public class WordUtils {


    /**
     * 为表格插入数据，行数不够添加新行
     * @param table 需要插入数据的表格
     * @param daList 第一个表格的插入数据
     */
    public static void insertTable(XWPFTable table, List<String[]> daList){
       
        CTTblBorders borders = table.getCTTbl().getTblPr().addNewTblBorders();
        // 内部表格水平居中
        table.getCTTbl().getTblPr().addNewJc().setVal(STJc.CENTER); 
        
        borders = allAddNewInsideV(borders);
        //创建行和创建需要的列
        for(int i = 0; i < daList.size(); i++){
            //添加一个新行
            XWPFTableRow row = table.insertNewTableRow(1);
            for(int k=0; k<daList.get(0).length;k++){
                //根据String数组第一条数据的长度动态创建列
                row.createCell();
            }
        }
        for(int i = 0; i < daList.size(); i++){
            List<XWPFTableCell> cells = table.getRow(i+1).getTableCells();
            for(int j = 0; j < cells.size(); j++){
                XWPFTableCell cell = cells.get(j);
                /** 设置水平居中 */
                /*CTTc cttc = cell.getCTTc();
                CTTcPr ctPr = cttc.addNewTcPr();
                ctPr.addNewVAlign().setVal(STVerticalJc.CENTER);
                cttc.getPList().get(0).addNewPPr().addNewJc().setVal(STJc.CENTER);*/
                cell.setText(daList.get(i)[j]);
                // 单元格垂直居中
                cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);    
                
            }
        }

       
        table.getCTTbl().getTblPr().setTblBorders(borders);
//        CTTblBorders borders = table.getCTTbl().getTblPr().addNewTblBorders();
//        CTTblBorders borders = table.getCTTbl().getTblPr().getTblBorders();
    }
    
    /**
     * 替换段落文本
     *
     * @param document docx解析对象
     * @param textMap  需要替换的信息集合
     */
    public static void changeText(XWPFDocument document, Map<String, Object> textMap) {
        //获取段落集合
        List<XWPFParagraph> paragraphs = document.getParagraphs();
        
        for (XWPFParagraph paragraph : paragraphs) {
            //获取到段落中的所有文本内容
            String text = paragraph.getText();
            //判断此段落中是否有需要进行替换的文本
            if (checkText(text)) {
                List<XWPFRun> runs = paragraph.getRuns();
                for (XWPFRun run : runs) {
                    //替换模板原来位置
                    String value = (String) changeValue(text, textMap);
                    //分段显示的情况
                    String[] values = value.split("\r\n");
                    if(values.length > 1) {
                        run.setText(values[0],0);
                        for (int i = 1; i < values.length; i++) {
                            //存在分段则新建一个run
                            XWPFRun newrun = paragraph.insertNewRun(i);
                            //copy样式
                            newrun.getCTR().setRPr(run.getCTR().getRPr());
                            //换行
                            newrun.addBreak();
                            //缩进
                            newrun.addTab();
                            newrun.setText(values[i]);
                        }
                        break;
                    }else {
                        //设置为粗体
//                        run.setBold(true);
                        run.setText(value,0);
                    }
                }
//                List<XWPFRun> runs = paragraph.getRuns();
//
//                for (XWPFRun run : runs) {
////                    run.addBreak();
//                    //替换模板原来位置
//                    String value = (String) changeValue(text, textMap);
//                    //分段显示的情况
//                    run.setText((String) changeValue(text, textMap), 0);
//                    
//                }
            }
        }
    }
    
    public static boolean checkText(String text) {
        boolean check = false;
        if (text.indexOf("$") != -1) {
            check = true;
        }
        return check;
    }

    public static Object changeValue(String value, Map<String, Object> textMap) {
        Set<Map.Entry<String, Object>> textSets = textMap.entrySet();
        Object valu = "";
        for (Map.Entry<String, Object> textSet : textSets) {
            //匹配模板与替换值 格式${key}
            String key = textSet.getKey();
            if (value.indexOf(key) != -1) {
                valu = textSet.getValue();
            }
        }
        return valu;
    }
    
    /**
     * 添加边框
     * @param borders
     * @return
     */
    public static CTTblBorders allAddNewInsideV(CTTblBorders borders ){

        CTBorder hBorder = borders.addNewInsideH();
        // 线条类型
        hBorder.setVal(STBorder.Enum.forString("single"));
        // 线条大小
        hBorder.setSz(new BigInteger("1"));
        // 设置颜色
        hBorder.setColor("000000");

        CTBorder vBorder = borders.addNewInsideV();
        vBorder.setVal(STBorder.Enum.forString("single"));
        vBorder.setSz(new BigInteger("1"));
        vBorder.setColor("000000");

        CTBorder lBorder = borders.addNewLeft();
        lBorder.setVal(STBorder.Enum.forString("single"));
        lBorder.setSz(new BigInteger("1"));
        lBorder.setColor("000000");

        CTBorder rBorder = borders.addNewRight();
        rBorder.setVal(STBorder.Enum.forString("single"));
        rBorder.setSz(new BigInteger("1"));
        rBorder.setColor("000000");

        CTBorder tBorder = borders.addNewTop();
        tBorder.setVal(STBorder.Enum.forString("single"));
        tBorder.setSz(new BigInteger("1"));
        tBorder.setColor("000000");

        CTBorder bBorder = borders.addNewBottom();
        bBorder.setVal(STBorder.Enum.forString("single"));
        bBorder.setSz(new BigInteger("1"));
        bBorder.setColor("000000");

        return borders;
    }


}
