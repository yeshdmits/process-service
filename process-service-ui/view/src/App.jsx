import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import { pdfjs } from 'react-pdf';
import 'react-pdf/dist/Page/TextLayer.css';
import 'react-pdf/dist/Page/AnnotationLayer.css';
import ProcessMain from './components/process/ProcessMain';
import { ApiContextProvider } from './context/ApiContext';
import UserAccount from './components/user/UserAccount';
import NavBar from './components/user/NavBar';

pdfjs.GlobalWorkerOptions.workerSrc = new URL(
    'pdfjs-dist/build/pdf.worker.min.js',
    import.meta.url,
).toString();

function App() {
    return (
        <BrowserRouter>
            <ApiContextProvider>
                <NavBar />
                <main className="max-h-[80vh]">
                    <Routes>
                        <Route path="/" element={<ProcessMain />} />
                        <Route path="/account" element={<UserAccount />} />
                    </Routes>
                </main>
            </ApiContextProvider>
        </BrowserRouter>
    );
}

export default App;
