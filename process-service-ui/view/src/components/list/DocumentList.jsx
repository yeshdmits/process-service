import React from "react";
import DocumentStatus from "../status/DocumentStatus";
import { formatDate } from "../../service/Utils";
import Header from "./Header.component";
import List, { ListRow, ListCell } from "./List.component";

const DocumentList = ({ documentList, handleViewDocument }) => {
    const headers = ["Document Name", "Last Action", "Updated By", "Status"];
    const items = documentList && 
        documentList.map((doc, id) =>
            <div key={id} onClick={() => handleViewDocument(doc)}
                className={ListRow}>
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
            </div>
        )

    return (
        <>
            <Header name={"Document Overview"}>
                <List header={headers} items={items} />
            </Header>
        </>
    )
}

export default DocumentList;