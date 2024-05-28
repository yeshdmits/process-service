import React, { useState } from "react";
import Form from '@rjsf/core';
import validator from '@rjsf/validator-ajv8';
import './custom-rjsf-styles.css';
import { Button } from "./TaskOverview";


const TaskCompleteJson = ({ task, readOnly, submitTask }) => {
    const [schema] = useState(JSON.parse(task.schema));
    const [formData, setFormData] = useState(JSON.parse(task.content));
    const [isFormValid, setIsFormValid] = React.useState(false);

    const handleChange = (value) => {
        setFormData(value)
        setIsFormValid(validator.isValid(schema, value, schema));
    }

    const handleSubmit = () => {
        if (isFormValid && !readOnly) {
            submitTask(JSON.stringify(formData), formData.decision === undefined ? "completed" : formData.decision === "Approved" ? "completed" : 'rejected')
        }
    }

    return (
        <>
            <div className="w-full flex justify-center overflow-y-auto">
                <Form
                    schema={schema}
                    validator={validator}
                    formData={formData}
                    liveOmit={true}
                    disabled={readOnly}
                    liveValidate
                    showErrorList={false}
                    onChange={(e) => handleChange(e.formData)}
                    children={true}
                />

            </div>

            <div className="flex sticky bottom-0 items-center justify-around">
                <div className={Button}
                    onClick={handleSubmit}>Submit</div>
            </div>
        </>
    );
}

export default TaskCompleteJson;