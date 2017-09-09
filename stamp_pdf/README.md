本工具类是需要使用到itextpdf的jar包来完成pdf文档的盖章

**maven依赖**

```markdown
<dependency>
    <groupId>com.itextpdf</groupId>
    <artifactId>itextpdf</artifactId>
    <version>5.5.7</version>
</dependency>
```

注意：一般盖章为了不遮挡住文字，需要对图片进行透明化处理