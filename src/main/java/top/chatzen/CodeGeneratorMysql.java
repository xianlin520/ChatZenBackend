package top.chatzen;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.sql.Types;

public class CodeGeneratorMysql {
    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://121.41.122.33:3306/chatzen?useSSL=false", "chatzen", "EdH38Q4HEsGA77Bs")
                .globalConfig(builder -> {
                    builder.author("XianLin") // 设置作者
                            .outputDir("C:\\Users\\wanga\\IdeaProjects\\chat-zen-spring\\src\\main\\java"); // 指定输出目录
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
                                .entity("model")    // 设置实体类包名
                                .mapper("dao")    // 设置Mapper包名
                                .service("service") // 设置Service包名
                                .serviceImpl("service.impl")    // 设置Service Impl包名
                                .controller("controller")    // 设置Controller包名
                                .xml("mapper")  // 设置Mapper XML包名
                                
                )
                .strategyConfig(builder ->
                        builder.addInclude("t_simple") // 设置需要生成的表名
                                .addTablePrefix("t_", "c_") // 设置过滤表前缀
                )
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
