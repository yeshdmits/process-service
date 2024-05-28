import React, { useEffect, useState } from "react";
import { Document, Page } from 'react-pdf';
import { useApiContext } from "../../context/ApiContext";
import { useNavigate, useSearchParams,createSearchParams } from 'react-router-dom';
import Header from "../list/Header.component";
import GoBack from '../../svgs/go-back.svg?react'

const ViewPdfComponent = () => {
    const navigate = useNavigate();
    const [searchParams] = useSearchParams();
    const [numPages, setNumPages] = useState(null);
    const [pageNumber] = useState(1);
    const [pdfUrl, setPdfUrl] = useState(null);
    const { getDocContent } = useApiContext();

    const onDocumentLoadSuccess = (value) => {
        setNumPages(value.numPages);
    }

    const handleBack = () => {
        if (searchParams.get("taskId")) {
            navigate({
                pathName: "/process/overview",
                search: `?${createSearchParams({ taskId: searchParams.get("taskId") })}`
            },
                {
                    state: {
                        addProduct: false,
                        features: false,
                        process: false,
                        task: true,
                        doc: false
                    }
                })
        } else if (searchParams.get("processId")) {
            navigate({
                pathName: "/process/overview",
                search: `?${createSearchParams({ processId: searchParams.get("processId") })}`
            },
                {
                    state: {
                        addProduct: false,
                        features: false,
                        process: true,
                        task: false,
                        doc: false
                    }
                })
        }
    }

    useEffect(() => {
        const renderDocument = () => {
            getDocContent(searchParams.get("docId"))
                .then((response) => {
                    setPdfUrl(window.URL.createObjectURL(response));
                })
        }
        renderDocument();
    }, [searchParams.get("docId")]);

    return (
        <Header name="Document Overview" elem={<GoBack onClick={handleBack} />}>
            {pdfUrl && <Document
                className="text-center"
                file={pdfUrl}
                onLoadSuccess={onDocumentLoadSuccess}
            >
                <Page scale={1} pageNumber={pageNumber} />
            </Document>
            }
            <p>Page {pageNumber} of {numPages}</p>
        </Header>
    );
}

export default ViewPdfComponent;