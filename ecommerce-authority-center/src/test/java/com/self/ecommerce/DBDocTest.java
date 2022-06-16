package com.self.ecommerce;

import cn.smallbun.screw.core.Configuration;
import cn.smallbun.screw.core.engine.EngineConfig;
import cn.smallbun.screw.core.engine.EngineFileType;
import cn.smallbun.screw.core.engine.EngineTemplateType;
import cn.smallbun.screw.core.execute.DocumentationExecute;
import cn.smallbun.screw.core.process.ProcessConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.util.Collections;

/**
 * 数据库表文档生成
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class DBDocTest {

    @Autowired
    ApplicationContext applicationContext;

    @Test
    public void buildDoc() throws IOException {
        File f = new File("");
        String dir = f.getCanonicalPath();
        //
        DataSource dataSource = applicationContext.getBean(DataSource.class);
        EngineConfig engineConfig = EngineConfig.builder()
                // 生成文件路径
                .fileOutputDir(dir)
                // 打开目录
                .openOutputDir(true)
                // 生成的文件类型
                .fileType(EngineFileType.HTML)
                // 文档类型
                .produceType(EngineTemplateType.freemarker)
                .build();
        // 生成文档配置，包含自定义版本号，描述等等
        Configuration config = Configuration.builder()
                .version("v1.0")
                .description("数据库文档描述")
                .dataSource(dataSource)
                .engineConfig(engineConfig)
                .produceConfig(getProcessConfig())
                .build();
        // 执行生成
        new DocumentationExecute(config).execute();
    }

    /**
     * 配置想要生成的数据表和想要忽略的数据表
     */
    private ProcessConfig getProcessConfig(){
        return ProcessConfig.builder()
                // 根据指定表名生成
                .designatedTableName(Collections.emptyList())
                // 根据指定表名前缀生成
                .designatedTablePrefix(Collections.emptyList())
                // 根据指定表名后缀生成
                .designatedTableSuffix(Collections.emptyList())
                // 忽略指定表名
                .ignoreTableName(Collections.emptyList())
                // 忽略指定表名前缀
                .ignoreTablePrefix(Collections.emptyList())
                // 忽略指定表名后缀
                .ignoreTableSuffix(Collections.emptyList())
                .build();
    }

}
