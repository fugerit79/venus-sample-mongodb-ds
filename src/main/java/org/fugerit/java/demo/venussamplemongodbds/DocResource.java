package org.fugerit.java.demo.venussamplemongodbds;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.fugerit.java.doc.base.config.DocConfig;
import org.fugerit.java.doc.base.process.DocProcessContext;

import java.io.ByteArrayOutputStream;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Slf4j
@ApplicationScoped
@Path("/doc")
public class DocResource {

    DocHelper docHelper;

    MongoClient mongoClient;

    public DocResource( DocHelper docHelper, MongoClient mongoClient) {
        this.docHelper = docHelper;
        this.mongoClient = mongoClient;
    }

    private MongoDatabase getDatabase() {
        return this.mongoClient.getDatabase( "venus-sample-mongodb-ds" );
    }

    private String venusPeopleDataModelById( String id ) {
        MongoCollection<Document> collection = this.getDatabase().getCollection( "venusPeopleDataModel" );
        FindIterable<Document> result = collection.find( Filters.eq( "id", id ) );
        try ( MongoCursor<Document> cursor = result.iterator() ) {
            if ( cursor.hasNext() ) {
                return cursor.next().toJson();
            } else {
                return null;
            }
        }
    }

    byte[] processDocument(String handlerId) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            String documentId = "people-sample-001";    // mongo db document id
            String jsonDataModel = this.venusPeopleDataModelById( documentId );
            log.info( "processDocument handlerId : {}", handlerId );
            String chainId = "document";
            // output generation
            this.docHelper.getDocProcessConfig().fullProcess(chainId, DocProcessContext.newContext("jsonDataModel", jsonDataModel), handlerId, baos);
            // return the output
            return baos.toByteArray();
        } catch (Exception e) {
            String message = String.format("Error processing %s, error:%s", handlerId, e);
            log.error(message, e);
            throw new WebApplicationException(message, e);
        }
    }

    @APIResponse(responseCode = "200", description = "The Markdown document content" )
    @APIResponse(responseCode = "500", description = "In case of an unexpected error" )
    @Tag( name = "document" )
    @Tag( name = "markdown" )
    @Operation( operationId = "MarkdownExample", summary = "Example Markdown generation",
        description =  "Generates an example Markdown document using Fugerit Venus Doc handler" )
    @GET
    @Produces("text/markdown")
    @Path("/example.md")
    public byte[] markdownExample() {
        return processDocument(DocConfig.TYPE_MD);
    }

    @APIResponse(responseCode = "200", description = "The HTML document content" )
    @APIResponse(responseCode = "500", description = "In case of an unexpected error" )
    @Tag( name = "document" )
    @Tag( name = "html" )
    @Operation( operationId = "HTMLExample", summary = "Example HTML generation",
        description =  "Generates an example HTML document using Fugerit Venus Doc handler" )
    @GET
    @Produces("text/html")
    @Path("/example.html")
    public byte[] htmlExample() {
        return processDocument(DocConfig.TYPE_HTML);
    }

    @APIResponse(responseCode = "200", description = "The AsciiDoc document content" )
    @APIResponse(responseCode = "500", description = "In case of an unexpected error" )
    @Tag( name = "document" )
    @Tag( name = "asciidoc" )
    @Operation( operationId = "AsciiDocExample", summary = "Example AsciiDoc generation",
        description =  "Generates an example AsciiDoc document using Fugerit Venus Doc handler" )
    @GET
    @Produces("text/asciidoc")
    @Path("/example.adoc")
    public byte[] asciidocExample() {
        return processDocument(DocConfig.TYPE_ADOC);
    }

    @APIResponse(responseCode = "200", description = "The PDF document content" )
    @APIResponse(responseCode = "500", description = "In case of an unexpected error" )
    @Tag( name = "document" )
    @Tag( name = "pdf" )
    @Operation( operationId = "PDFExample", summary = "Example PDF generation",
        description =  "Generates an example PDF document using Fugerit Venus Doc handler" )
    @GET
    @Produces("application/pdf")
    @Path("/example.pdf")
    public byte[] pdfExample() {
        return processDocument(DocConfig.TYPE_PDF);
    }




}
