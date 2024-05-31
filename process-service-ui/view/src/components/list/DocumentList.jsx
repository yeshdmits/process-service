import React from "react";
import DocumentStatus from "../status/DocumentStatus";
import { formatDate } from "../../service/Utils";
import Header from "./Header.component";
import List, { ListRow5, ListCell } from "./List.component";
import Download from "../../svgs/download.svg?react";
import { useApiContext } from "../../context/ApiContext";

export const DocItems = (documentList, handleViewDocument) => {
    const {handleDownloadDocument} = useApiContext();
    const onDownload = (event, doc) => {
        event.stopPropagation();
        handleDownloadDocument(doc);

    }
    return documentList && 
    documentList.map((doc, id) =>
        <div key={id} onClick={() => handleViewDocument(doc)}
            className={ListRow5}>
            <div className={ListCell}>
                {doc.documentName}
            </div>
            <div className={ListCell}>
                {formatDate(doc.modifiedAt)}
            </div>
            <div className={ListCell}>
                {doc.modifiedBy}
            </div>
            <div className={ListCell}>
                <DocumentStatus docStatus={doc.documentStatus} />
            </div>
            <div className={`${ListCell} hover:bg-gray-300`} onClick={(event) => onDownload(event, doc)}>
                <Download fill="blue" width="20px" height="20px"/>
            </div>
        </div>
    );
}


const DocumentList = ({ documentList, handleViewDocument }) => {
    const headers = ["Name", "Last Action", "Updated By", "Status", "."];
    const items = DocItems(documentList, handleViewDocument)

    return (
        <>
            <Header name={"Document Overview"}>
                <List header={headers} items={items} />
            </Header>
        </>
    )
}

export default DocumentList;