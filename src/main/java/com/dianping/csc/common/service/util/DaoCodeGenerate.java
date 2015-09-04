package com.dianping.csc.common.service.util;

import com.dianping.csc.common.service.entity.EntityTest;
import com.google.common.collect.Maps;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.*;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by csophys on 15/8/28.
 */
public class DaoCodeGenerate {

    public static final String CONFIG_SPRING_LOCAL_APPCONTEXT_CSC_DAO_XML = "/config/spring/local/appcontext-dao.xml";
    public static final String CONFIG_SQLMAP_SQLMAP_CONFIG_XML = "/config/sqlmap/sqlmap-config.xml";
    public static final String SQL_MAP_RESOURCE_CONFIG_SQLMAP_SQLMAP_ENTITY_ID_XML = "<sqlMap resource=\"config/sqlmap/sqlmap-entityID.xml\" />";
    private static Logger logger = Logger.getLogger(DaoCodeGenerate.class);
    private static SAXReader saxReader = new SAXReader();

    public static void generateByJavaBean(Class clazz) {
        //获取当前文件目录
        File file = getCurrentDirectory(clazz);

        //获取源文件目录
        File sourceDirectory = getSourceDirectory(file);

        logger.debug("获取的源代码目录path:" + sourceDirectory.getAbsolutePath());

        //freemarker配置设置
        Configuration configuration = getFreeMarkerConfiguration(clazz);

        //生成dao
        generateDao(clazz, file, configuration);
        logger.info("dao类生成成功！");
        //生成dao配置
        generateSpringDaoConfig(clazz, sourceDirectory, configuration);
        logger.info("spring dao config生成成功！");

        //生成sqlmap-config
        generateSqlmapConfig(clazz, sourceDirectory);
        logger.info("ibatis sqlmap config生成成功！");

        //生成sqlmap
        generateSqlmap(clazz, sourceDirectory, configuration);
        logger.info("ibatis sqlmap 生成成功!");

        //生成test
    }

    private static void generateSqlmap(Class clazz, File sourceDirectory, Configuration configuration) {
        String sqlmapPath = getResourceDirectory(sourceDirectory) + "/config/sqlmap/sqlmap-" + getEntityID(clazz) + ".xml";
        File sqlmapFile = new File(sqlmapPath);

        if (sqlmapFile.exists()) {
            sqlmapFile.delete();
        }
        try {
            sqlmapFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Template template = null;

        try {
            template = configuration.getTemplate("sqlmap.ftl");
        } catch (IOException e) {
            e.printStackTrace();
        }
        HashMap<Object, Object> map = Maps.newHashMap();
        map.put("entity", clazz.getName());
        map.put("entitySimple", clazz.getSimpleName());
        map.put("entityFields", clazz.getDeclaredFields());
        try {
            template.process(map, new OutputStreamWriter(new FileOutputStream(sqlmapFile)));
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void generateSqlmapConfig(Class clazz, File sourceDirectory) {
        String sqlmapConfigPath = getResourceDirectory(sourceDirectory) + CONFIG_SQLMAP_SQLMAP_CONFIG_XML;
        File sqlmapConfigFile = new File(sqlmapConfigPath);
        String entityID = getEntityID(clazz);
        String sqlmapConfigContent = SQL_MAP_RESOURCE_CONFIG_SQLMAP_SQLMAP_ENTITY_ID_XML.replace("entityID", entityID);
        doGenerateConfig(sqlmapConfigFile, entityID, sqlmapConfigContent);
    }

    private static String getEntityID(Class clazz) {
        return clazz.getSimpleName().substring(0, 1).toLowerCase() + clazz.getSimpleName().substring(1);
    }

    private static void generateSpringDaoConfig(Class clazz, File file, Configuration configuration) {
        String springDaoConfigPath = getResourceDirectory(file) + CONFIG_SPRING_LOCAL_APPCONTEXT_CSC_DAO_XML;
        logger.debug("springDaoConfigPath：" + springDaoConfigPath);
        File springDaoConfigFile = new File(springDaoConfigPath);
        String daoID = getDaoSimpleName(clazz).substring(0, 1).toLowerCase() + getDaoSimpleName(clazz).substring(1);

        doGenerateConfig(springDaoConfigFile, daoID, getDaoBean(clazz, configuration, daoID).toString());
    }


    /**
     * 生成配置
     *
     * @param file
     * @param id
     * @param context
     */
    private static void doGenerateConfig(File file, String id, String context) {
        if (!file.exists()) {
            logger.error("spring dao 配置文件不存在");
            return;
        } else {
            try {
                Document document = saxReader.read(file);
                Element rootElement = document.getRootElement();

                //如果daobean 已经存在
                if (document.asXML().indexOf(id) != -1) {
                    logger.warn("ID已经存在");
                    return;
                    /**
                     * TODO:xpath 问题
                     */
                    /*Element daoBean = (Element) document.selectSingleNode("//bean[@id='"+id+"']");

                    if (daoBean != null) {
                        logger.warn("daoID已经存在");
                        document.remove(daoBean);
                    }*/
                }

                OutputFormat format = OutputFormat.createPrettyPrint();
                XMLWriter xmlWriter = null;
                //TODO:xml输出格式化
                //format.setTrimText(false);
                try {
                    xmlWriter = new XMLWriter(new FileOutputStream(file), format);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                xmlWriter.setEscapeText(false);
                rootElement.addText(context);
                try {
                    xmlWriter.write(document);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (DocumentException e) {
                e.printStackTrace();
            }
        }
    }

    private static StringWriter getDaoBean(Class clazz, Configuration configuration, String daoID) {
        //重新渲染daobean temlate
        HashMap<Object, Object> map = Maps.newHashMap();
        map.put("daoID", daoID);
        map.put("dao", getDaoPackage(clazz) + "." + getDaoSimpleName(clazz));
        map.put("entitySimple", clazz.getSimpleName());
        Template template = null;
        try {
            template = configuration.getTemplate("spring-dao-bean.ftl");
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringWriter stringWriter = null;
        try {
            stringWriter = new StringWriter();
            template.process(map, stringWriter);
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringWriter;
    }

    private static void generateDao(Class clazz, File file, Configuration configuration) {
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
            map.put("package", getDaoPackage(clazz));
            map.put("entitySimple", clazz.getSimpleName());
            map.put("entity", clazz.getName());
            map.put("daoSimple", getDaoSimpleName(clazz));
            Writer writer = new OutputStreamWriter(new FileOutputStream(daoFile));
            try {
                template.process(map, writer);
                writer.flush();
            } catch (TemplateException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getDaoPackage(Class clazz) {
        return clazz.getName().replaceAll("\\.[a-zA-Z]*\\." + clazz.getSimpleName(), ".dao");
    }

    private static String getDaoSimpleName(Class clazz) {
        return clazz.getSimpleName() + "DAO";
    }

    private static Configuration getFreeMarkerConfiguration(Class clazz) {
        Configuration configuration = new Configuration();
        try {
            //TODO:freemarker 读取jar中的ftl暂时不能实现
            URL resource = clazz.getResource("/config/ftl");
            configuration.setDirectoryForTemplateLoading(new File(resource.getFile().replace("%20", " ")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return configuration;
    }

    private static String getResourceDirectory(File sourceDirectory) {
        return sourceDirectory.getParentFile() + "/resources";
    }

    private static File getSourceDirectory(File file) {
        String absolutePath = file.getAbsolutePath();
        /**
         * jar 中的目录结构不一样，需要特别处理
         */
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
}
