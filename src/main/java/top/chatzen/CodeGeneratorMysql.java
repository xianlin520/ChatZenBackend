package top.chatzen;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.sql.Types;

public class CodeGeneratorMysql {
    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://127.0.0.1:3306/chatzen?useSSL=false&allowPublicKeyRetrieval=true", "root", "root")
                .globalConfig(builder -> {
                    builder.author("XianLin") // 设置作者
                            .outputDir("C:\\Users\\wanga\\IdeaProjects\\chat-zen-spring\\src\\main\\java")  // 指定输出目录
//                            .outputDir("C:\\Users\\wanga\\IdeaProjects\\chat-zen-spring\\temp")  // 指定输出目录
                            .disableOpenDir();  // 禁止打开输出目录
                })
                .dataSourceConfig(builder ->
                        builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                            int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                            if (typeCode == Types.SMALLINT) {
                                // 自定义类型转换
                                return DbColumnType.INTEGER;
                            }
                            return typeRegistry.getColumnType(metaInfo);
                        })
                )
                .packageConfig(builder ->
                        builder.parent("top.chatzen")  // 设置父包名
                                .entity("entity")    // 设置实体类包名
                                .mapper("dao")    // 设置Mapper包名
                                .service("service") // 设置Service包名
                                .serviceImpl("service.impl")    // 设置Service Impl包名
                                .xml("mapper")  // 设置Mapper XML包名
                
                )
                .strategyConfig(builder ->
                        builder.addInclude("t_user_status") // 设置需要生成的表名
                                .addTablePrefix("t_") // 设置过滤表前缀
                                .entityBuilder()
                                .enableLombok()
                                .enableTableFieldAnnotation()
                )
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
