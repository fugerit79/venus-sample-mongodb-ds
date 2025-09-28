<?xml version="1.0" encoding="utf-8"?>
<doc
        xmlns="http://javacoredoc.fugerit.org"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://javacoredoc.fugerit.org https://www.fugerit.org/data/java/doc/xsd/doc-2-1.xsd" >

    <#--
        This is a Venus Fugerit Doc (https://github.com/fugerit-org/fj-doc) FreeMarker Template XML (ftl[x]).
        For consideration of Venus Fugerit Doc and Apache FreeMarker integration see :
        https://venusdocs.fugerit.org/guide/#doc-freemarker-entry-point
        The result will be a :
    -->
    <!--
        This is a Venus Fugerit Doc (https://github.com/fugerit-org/fj-doc) XML Source Document.
        For documentation on how to write a valid Venus Doc XML Meta Model refer to :
        https://venusdocs.fugerit.org/guide/#doc-format-entry-point
    -->

    <#--
        this will convert json string to json
        https://freemarker.apache.org/docs/ref_builtins_expert.html#ref_builtin_eval
        https://freemarker.apache.org/docs/ref_builtins_expert.html#ref_builtin_eval_json
    -->
    <#assign jsonDataModel = jsonDataModel?eval_json>

    <#assign docTitle=jsonDataModel.docTitle>

    <#assign listPeople=jsonDataModel.listPeople>

    <metadata>
        <!-- Margin for document : left;right;top;bottom -->
        <info name="margins">10;10;10;30</info>
        <!-- documenta meta information -->
        <info name="doc-title">${docTitle}</info>
        <info name="doc-subject">Fugerit Venus Doc Mongo DB Data Source - Template XML - ftlx</info>
        <info name="doc-author">fugerit79</info>
        <info name="doc-language">en</info>
        <!-- property specific for xls/xlsx -->
        <info name="excel-table-id">data-table=print</info>
        <!-- property specific for csv -->
        <info name="csv-table-id">data-table</info>
        <footer-ext>
            <para align="right">${r"${currentPage}"} / ${r"${pageCount}"}</para>
        </footer-ext>
    </metadata>
    <body>
    <para>${docTitle!defaultTitle}</para>
    <table columns="3" colwidths="30;30;40"  width="100" id="data-table" padding="2">
        <row header="true">
            <cell align="center"><para>Name</para></cell>
            <cell align="center"><para>Surname</para></cell>
            <cell align="center"><para>Title</para></cell>
        </row>
        <#if listPeople??>
            <#list listPeople as current>
                <row>
                    <cell><para>${current.name}</para></cell>
                    <cell><para>${current.surname}</para></cell>
                    <cell><para>${current.title}</para></cell>
                </row>
            </#list>
        </#if>
    </table>
    </body>

</doc>