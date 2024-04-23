package com.basic.springai.service;

import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SetUpDataToDbService {

    private final VectorStore vectorStore;

    public String setUpDataToDb(Resource pdf) {
        PagePdfDocumentReader pdfReader= new PagePdfDocumentReader(pdf);
        TokenTextSplitter textSplitter = new TokenTextSplitter();
        List<Document> docs = textSplitter.apply(pdfReader.get());
        vectorStore.add(docs);
        return "Set data to DB success";
    }

    public String setUpDataToDb(MultipartFile pdf) {
        try {
            PDDocument document = PDDocument.load(pdf.getInputStream());
            PDFTextStripper textStripper = new PDFTextStripper();
            String text = textStripper.getText(document);
            List<Document> doc = List.of(new Document(text));
            document.close();
            vectorStore.add(doc);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "Set data to DB success";
    }
}