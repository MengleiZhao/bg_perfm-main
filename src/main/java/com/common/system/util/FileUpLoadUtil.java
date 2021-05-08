package com.common.system.util;

import ch.qos.logback.classic.net.SimpleSocketServer;
import com.common.system.entity.EntityDao;
import com.common.system.shiro.ShiroUser;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 附件工具栏
 *
 * @author 安达
 */
public class FileUpLoadUtil {
    private final static ResourceBundle res = ResourceBundle.getBundle("upload");
    
    public final static String WORKGROUP_TASKMGR = "1-绩效评价调研函";
    public final static String COLLECTION_MEANS = "2-资料收集";
    public final static String PLANMGR_INDEXDRAFTDESIGN = "3-方案制定";
    public final static String PLANMGR_PRE_MKLETTER = "4-预调研";
    public final static String PLANMGR_SCHEME_MKSCHEME = "5-编制评价工作方案";
    public final static String EVALUA_SITE_FILL = "6-底稿填写";
    public final static String EVALUA_CONCLUSIONS = "7-初步评价结论编写";
    public final static String REPORT_FILL = "8-编写评价报告";
    public final static String ARCHIVEMGR_FILL = "9-整理归档";    
    
    

    /**
     * 1.上传文件-->过程性文件
     * @param entity 保存的实体类
     * @param serviceName 模块名字 （如：2-资料收集）
     * @param file 上传的具体文件
     * @param user 当前登录人
     * @param idA 主子项目id值
     * @return
     * @throws Exception
     */
    public static EntityDao upload(EntityDao entity, String serviceName,
                                   MultipartFile file, ShiroUser user,Integer idA) throws Exception {
        String fileName = file.getOriginalFilename();//文件名
       // String contentType = file.getContentType();//文件类型
         String postfix = fileName.substring(fileName.lastIndexOf("."), fileName.length());//文件后缀
         String fn = UUID.randomUUID() + postfix;//系统文件名
        // String dateRoot = new SimpleDateFormat("yyyyMM").format(new Date());//把年月作为图片的底层目录

        //标记是什么文件（过程性文件[process]、模板文件[template]、绩效数据库文件[database]）
        String savePath=getSavePathByServiceType("process");
        String root = savePath + "/" +idA+"/"+serviceName;//文件存放目录
        File fileRoot = new File(root);
        if (!fileRoot.exists()) {//判断是否存在这个目录,不存在创建
            fileRoot.mkdirs();
        }
        String path = root  + "/" + fn;
        entity.setCreateor(user.getName());//文件上传者
        entity.setCreateTime(new Date());// 创建时间
        entity.setFileName(fileName);//文件名称
        entity.setFilePath(path);//文件路径
        entity.setFileSize(FileUpLoadUtil.getFileSize(file.getSize()));//文件大小
        //获取源文件名称
        String originalFilename = file.getOriginalFilename();
        File f = new File(path);
        try {
            file.transferTo(f);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entity;
    }



    /**
     * 2.上传文件-->绩效数据库文件
     * @param entity 保存的实体类
     * @param serviceName 模块名字 （如：1-政策法规库、2-绩效指标库、3-项目人员库 等等）
     * @param file 上传的具体文件
     * @param user 当前登录人
     * @return EntityDao
     * @author 田鑫艳
     * @createTime 2021/3/29 14:18
     * @updateTime 2021/3/30 20:48
     */
    public static EntityDao databaseUpload(EntityDao entity, String serviceName, MultipartFile file, ShiroUser user) {
        String fileName = file.getOriginalFilename();//文件名
        String postfix = fileName.substring(fileName.lastIndexOf("."), fileName.length());//文件后缀
        String fn = UUID.randomUUID() + postfix;//系统文件名

        //1.标记是什么文件（过程性文件[process]、模板文件[template]、绩效数据库文件[database]）
        String savePath=getSavePathByServiceType("database");
        //2.绩效数据库  upload.database.save_path=D:/attachment/database
        //文件存放目录：D:/attachment/database/1-政策法规库
        String root = savePath + "/" +serviceName;
        File fileRoot = new File(root);
        if (!fileRoot.exists()) {//判断是否存在这个目录,不存在创建
            fileRoot.mkdirs();
        }

        //3.最终文件存放的样子：D:/attachment/database/1-政策法规库/字段生成的文件编码值.文件后缀
        String path = root  + "/" + fn;
        entity.setCreateor(user.getName());//文件上传者
        entity.setCreateTime(new Date());// 创建时间
        entity.setFileName(fileName);//文件名称
        entity.setFilePath(path);//文件路径
        entity.setFileSize(FileUpLoadUtil.getFileSize(file.getSize()));//文件大小
        //获取源文件名称
        String originalFilename = file.getOriginalFilename();
        //保存到本地服务器中
        File f = new File(path);
        try {
            file.transferTo(f);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entity;
    }

    /**
     * 3.生成新的文件目录（从旧的目录中复制到绩效数据库目录中）
     * @param oldOriginalFilename 源文件名
     * @param serviceName         模块名字 （如：1-政策法规库、2-绩效指标库、3-项目人员库 等等）
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/31 11:01
     * @updateTime 2021/3/31 11:01
     */
    public static String newDatabasePath(String oldOriginalFilename,String serviceName){

        String savePath=getSavePathByServiceType("database");//标注是绩效数据库文件
        String root = savePath + "/" +serviceName;//D:/attachment/database/1-政策法规库

        String postfix = oldOriginalFilename.substring(oldOriginalFilename.lastIndexOf("."), oldOriginalFilename.length());//文件后缀
        String fn = UUID.randomUUID() + postfix;//系统文件名-->字段生成的文件编码值.文件后缀
        //最终文件存放的样子：D:/attachment/database/1-政策法规库/字段生成的文件编码值.文件后缀
        String newPath = root  + "/" + fn;
        return newPath;
    }


    /**
     * 4.将一个文件从一个目录复制到另一个目录 （复制单个文件）
     * @param oldPath 旧的目录
     * @param newPath 复制到新的目录
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/31 10:32
     * @updateTime 2021/3/31 10:32
     */
    public static void copyFile(String oldPath, String newPath) {
        try {

            int bytesum = 0;
            int byteread = 0;
            InputStream inStream;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时
                 inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                int length;
                while ( (byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }

                //关闭流
                try {
                    if(inStream!=null){
                        inStream.close();
                    }
                }catch (Exception e){
                    System.out.println("关闭InputStream出错");
                    e.printStackTrace();
                }finally {
                    if(inStream!=null){
                        inStream.close();
                    }
                }

            }

        }catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();

        }
    }










    public static EntityDao upload(EntityDao entity, String serviceType,
                                   MultipartFile file, ShiroUser user) throws Exception {
        String fileName = file.getOriginalFilename();//文件名
        // String contentType = file.getContentType();//文件类型
        String postfix = fileName.substring(fileName.lastIndexOf("."), fileName.length());//文件后缀
        String fn = UUID.randomUUID() + postfix;//系统文件名
        String dateRoot = new SimpleDateFormat("yyyyMM").format(new Date());//把年月作为图片的底层目录
        String savePath=getSavePathByServiceType(serviceType);
        String root = savePath + "\\" + dateRoot + "\\";//文件存放目录
        File fileRoot = new File(root);
        if (!fileRoot.exists()) {//判断是否存在这个目录,不存在创建
            fileRoot.mkdirs();
        }
        String path = savePath + "\\" + dateRoot + "\\" + fn;
        entity.setCreateor(user.getName());//文件上传者
        entity.setCreateTime(new Date());// 创建时间
        entity.setFileName(fileName);//文件名称
        entity.setFilePath(path);//文件路径
        entity.setFileSize(FileUpLoadUtil.getFileSize(file.getSize()));//文件大小
        File f = new File(path);
        try {
            file.transferTo(f);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entity;
    }

    /*
     * 根据目录编号获取上传存子目录
     * @author 安达
     * @createtime 2021-3-4
     * @updatetime 2021-3-4
     */
    public static String getSavePathByServiceType(String serviceType) {
        //根据类型获取文件存储路径
        String path =null;
        try{
            path = res.getString("upload." + serviceType + ".save_path");
        }catch (Exception e){
            e.printStackTrace();
            path = res.getString("upload.default.save_path");
        }
        return path;
    }

    /**
     * 删除文件
     * @param path 路径
     * @param path
     * @return
     * @throws Exception
     */
    public static void deleteFile(String path) {
        File file = new File(path);
        if(file.exists()){
            file.delete();
        }

    }

    /**
     * 获取文件大小
     *
     * @param fileSize
     * @return
     */
    public static String getFileSize(long fileSize) {
        // 文件大小转换
        DecimalFormat df1 = new DecimalFormat("0.00");
        String fileSizeString = "0k";
        if (fileSize < 1024) {
            fileSizeString = df1.format((double) fileSize) + "B";
        } else if (fileSize < 1048576) {
            fileSizeString = df1.format((double) fileSize / 1024) + "K";
        } else if (fileSize < 1073741824) {
            fileSizeString = df1.format((double) fileSize / 1048576) + "M";
        } else {
            fileSizeString = df1.format((double) fileSize / 1073741824) + "G";
        }
        return fileSizeString;
    }



    /**
     * 1.下载文件
     * @param filePath 文件路径
     * @param fileName 文件名
     * @return
     * @author 田鑫艳
     * @createTime 2021/3/22 16:52
     * @updateTime 2021/3/22 16:52
     */
    public static String downLoadFile(String filePath,String fileName,HttpServletResponse response) throws IOException {
        response.setContentType("application/x-msdownload;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename="
                + URLEncoder.encode(fileName, "UTF-8"));
        File file = new File(filePath);
        ServletOutputStream out =null;
        if(!file.exists()){
            return "下载文件不存在";
        }else{
            try {
                FileInputStream in = new FileInputStream(file);//文件字节输入流

                out = response.getOutputStream();

                byte[] b = new byte[1024];
                int len;
                while((len = in.read(b)) != -1){
                    out.write(b, 0, len);
                }
                out.flush();
               return "下载文件成功！";
            } catch (IOException e) {
                e.printStackTrace();
                return "下载文件失败！";
            }finally {
                if(out!=null){
                    out.close();
                    return null;
                }
            }
        }

    }



}
