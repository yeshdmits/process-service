import React, { useEffect, useState } from "react";
import { Document, Page } from 'react-pdf';
import { useApiContext } from "../../context/ApiContext";
import { useNavigate, useSearchParams, createSearchParams } from 'react-router-dom';
import Header from "../list/Header.component";
import GoBack from '../../svgs/go-back.svg?react';
import Prev from '../../svgs/prev.svg?react';
import Next from '../../svgs/next.svg?react';

const NextPrev = "hover:cursor-pointer flex items-center hover:bg-gray-300 rounded-full";

const ViewPdfComponent = () => {
    const navigate = useNavigate();
    const [searchParams] = useSearchParams();
    const [numPages, setNumPages] = useState(null);
    const [pageNumber, setPageNumber] = useState(1);
    const [pdfUrl, setPdfUrl] = useState(null);
    const { getDocContent } = useApiContext();
    const [screenSize, setScreenSize] = useState({
        width: window.innerWidth,
        height: window.innerHeight,
    });

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

    const onPrev = () => {
        if (pageNumber === 1) {
            return;
        }
        setPageNumber(pageNumber - 1)
    }

    const onNext = () => {
        if (pageNumber === numPages) {
            return;
        }
        setPageNumber(pageNumber + 1)
    }

    useEffect(() => {
        const renderDocument = () => {
            getDocContent(searchParams.get("docId"))
                .then((response) => {
                    setPdfUrl(window.URL.createObjectURL(response));
                })
        }

        const handleResize = () => {
            setScreenSize({
                width: window.innerWidth,
                height: window.innerHeight,
            });
        };

        if (searchParams.get("docId")) {
            renderDocument();
            window.addEventListener('resize', handleResize);
        }

        return () => {
            window.removeEventListener('resize', handleResize);
          };
    }, []);

    return (
        <Header name="Document Overview" elem={<GoBack onClick={handleBack} />}>
            {pdfUrl && <Document
                className="text-center"
                file={pdfUrl}
                onLoadSuccess={onDocumentLoadSuccess}
            >
                <Page scale={screenSize.width >= 600 ? screenSize.width/1200  : screenSize.width/600} pageNumber={pageNumber} />
            </Document>
            }
            <div className="flex justify-around mb-3">
                <div className={NextPrev} onClick={onPrev}>
                    <Prev width="20px" height="20px" />
                </div>
                <div>Page {pageNumber} of {numPages}</div>
                <div className={NextPrev} onClick={onNext}>
                    <Next width="20px" height="20px" />
                </div>
            </div>
        </Header>
    );
}

export default ViewPdfComponent;