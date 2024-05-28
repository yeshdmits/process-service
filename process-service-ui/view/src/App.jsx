import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import { pdfjs } from 'react-pdf';
import 'react-pdf/dist/Page/TextLayer.css';
import 'react-pdf/dist/Page/AnnotationLayer.css';
import ProcessMain from './components/process/ProcessMain';
import { ApiContextProvider } from './context/ApiContext';

pdfjs.GlobalWorkerOptions.workerSrc = new URL(
    'pdfjs-dist/build/pdf.worker.min.js',
    import.meta.url,
).toString();

function App() {
    return (
        <BrowserRouter>
            <ApiContextProvider>
                <Routes>
                    <Route path="/" element={<ProcessMain />} />
                </Routes>
            </ApiContextProvider>
        </BrowserRouter>
    );
}

export default App;
