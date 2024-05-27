import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import HelloComponent from './components/HelloComponent';
import ProcessOverview from './components/process/ProcessOverview';
import TaskCompleteJson from './components/task/TaskCompleteJson';
import TaskCompleteCustom from './components/task/TaskCompleteCustom';
import { pdfjs } from 'react-pdf';
import 'react-pdf/dist/Page/TextLayer.css';
import 'react-pdf/dist/Page/AnnotationLayer.css';
import ProcessList from './components/process/ProcessList';

pdfjs.GlobalWorkerOptions.workerSrc = new URL(
    'pdfjs-dist/build/pdf.worker.min.js',
    import.meta.url,
).toString();

function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<HelloComponent />} />
                <Route path="/process" element={<ProcessOverview />} />
                <Route path="/process/task/json" element={<TaskCompleteJson />} />

                <Route path="/process/task/custom" element={<TaskCompleteCustom />} />

                <Route path="/process/list" element={<ProcessList />} />
            </Routes>
        </BrowserRouter>
    );
}

export default App;
