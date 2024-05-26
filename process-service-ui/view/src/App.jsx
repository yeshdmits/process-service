import React from 'react';
import { HashRouter, Routes, Route } from 'react-router-dom';
import HelloComponent from './components/HelloComponent';
import ProcessOverview from './components/process/ProcessOverview';
import TaskCompleteJson from './components/task/TaskCompleteJson';
import TaskCompleteCustom from './components/task/TaskCompleteCustom';
import { pdfjs } from 'react-pdf';
import 'react-pdf/dist/Page/TextLayer.css';
import 'react-pdf/dist/Page/AnnotationLayer.css';
import ProcessList from './components/process/ProcessList';
import LoginComponent from './components/login/Login';
import AuthLayout from './components/login/AuthLayout';

pdfjs.GlobalWorkerOptions.workerSrc = new URL(
    'pdfjs-dist/build/pdf.worker.min.js',
    import.meta.url,
).toString();

function App() {
    return (
        <HashRouter>
            <Routes>
                <Route path="/" element={<HelloComponent />} />
                <Route element={<AuthLayout url="/process" />}>
                    <Route path="/process" element={<ProcessOverview />} />
                </Route>
                <Route element={<AuthLayout url="/process/task/json" />}>
                    <Route path="/process/task/json" element={<TaskCompleteJson />} />
                </Route>

                <Route element={<AuthLayout url="/process/task/custom" />}>
                    <Route path="/process/task/custom" element={<TaskCompleteCustom />} />
                </Route>

                <Route element={<AuthLayout url="/process/list" />}>
                    <Route path="/process/list" element={<ProcessList />} />
                </Route>

                <Route path="/login" element={<LoginComponent />} />
            </Routes>
        </HashRouter>
    );
}

export default App;
