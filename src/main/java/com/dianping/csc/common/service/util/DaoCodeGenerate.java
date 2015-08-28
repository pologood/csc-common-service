package com.dianping.csc.common.service.util;

import com.google.common.collect.Maps;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by csophys on 15/8/28.
 */
public class DaoCodeGenerate {

    public static void generateByJavaBean(Class clazz) {
        //获取当前文件目录
        File file = getCurrentDirectory(clazz);

        //获取源文件目录
        File sourceDirectory = getSourceDirectory(file);


        //freemarker配置设置
        Configuration configuration = getFreeMarkerConfiguration(sourceDirectory);

        //生成dao
        generateDaoTemplate(clazz, file, configuration);

        //生成dao配置
        //生成sqlmap-config
        //生成sqlmap
        //生成test
    }

    private static void generateDaoTemplate(Class clazz, File file, Configuration configuration) {
        String daoFilePath = file.getParent() + "/dao/" + clazz.getSimpleName() + "DAO.java";
        File daoFile = new File(daoFilePath);
        try {
            if (daoFile.exists()) {
                daoFile.delete();
            }

            daoFile.createNewFile();
            Template template = null;
            try {
                template = configuration.getTemplate("dao.ftl");
            } catch (IOException e) {
                e.printStackTrace();
            }
            HashMap<Object, Object> map = Maps.newHashMap();
            map.put("package", clazz.getName().replaceAll("\\.[a-zA-Z]*\\." + clazz.getSimpleName(), ".dao"));
            map.put("entitySimple", clazz.getSimpleName());
            map.put("entity", clazz.getName());
            map.put("daoSimple", clazz.getSimpleName() + "DAO");
            Writer writer = new OutputStreamWriter(new FileOutputStream(daoFile));
            try {
                template.process(map,writer);
                writer.flush();
            } catch (TemplateException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Configuration getFreeMarkerConfiguration(File sourceDirectory) {
        Configuration configuration = new Configuration();
        try {
            configuration.setDirectoryForTemplateLoading(new File(sourceDirectory.getParentFile() + "/resources/ftl"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return configuration;
    }

    private static File getSourceDirectory(File file) {
        String absolutePath = file.getAbsolutePath();
        return new File(absolutePath.substring(0, absolutePath.lastIndexOf("java")) + "java");

    }

    private static File getCurrentDirectory(Class clazz) {
        /**
         * class.getName 获取类名（包含包）
         */
        URL url = clazz.getClassLoader().getResource(clazz.getName().replace(".", "/") + ".class");
        String currentDirectoryPath = url.toString().replace("file:", "").replace("target/classes", "src/main/java").replace("/" + clazz.getSimpleName() + ".class", "").replace("%20", " ");
        return new File(currentDirectoryPath);
    }

    public static void main(String[] args) {
        DaoCodeGenerate.generateByJavaBean(DaoCodeGenerate.class);
    }
}
