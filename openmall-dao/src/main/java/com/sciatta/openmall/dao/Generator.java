package com.sciatta.openmall.dao;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangxiaoyu on 2021/7/16<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * 用于生成MBG的代码
 */
public class Generator {
    public static void main(String[] args) throws XMLParserException, IOException,
            InvalidConfigurationException, SQLException, InterruptedException {
        
        // MBG 执行过程中的警告信息
        List<String> warnings = new ArrayList<String>();
        // 当生成的代码重复时，覆盖原代码
        boolean overwrite = true;
        // 逆向工程配
        Configuration config;
        
        // 指定逆向工程配置文件并解析
        try (InputStream is = Generator.class.getResourceAsStream("/generator/generatorConfig.xml")) {
            ConfigurationParser cp = new ConfigurationParser(warnings);
            config = cp.parseConfiguration(is);
        }
        
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        // 创建 MBG
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        // 执行生成代码
        myBatisGenerator.generate(null);
        
        // 输出警告信息
        for (String warning : warnings) {
            System.out.println(warning);
        }
    }
}
