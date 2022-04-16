package com.sciatta.openmall.portlet.mapper;

import com.sciatta.openmall.mapper.mbg.Generator;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Rain on 2022/3/14<br>
 * All Rights Reserved(C) 2017 - 2022 SCIATTA <br> <p/>
 * SearchGenerator
 */
public class SearchGenerator {
    public static void main(String[] args) throws XMLParserException, SQLException, IOException, InterruptedException, InvalidConfigurationException {
        Generator.generate("/generator/generatorConfig.xml");
    }
}
