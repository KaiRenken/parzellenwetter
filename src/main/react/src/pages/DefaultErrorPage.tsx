import {isRouteErrorResponse, useRouteError} from "react-router-dom";

const DefaultErrorPage = () => {
    const error = useRouteError() as any;

    if (isRouteErrorResponse(error)) {
        return (
            <div>
                <h1>Es ist ein Fehler aufgetreten:</h1>
                <h2>Status: {error.status} ({error.statusText})</h2>
                {error.data?.message && <p>Message: {error.data.message}</p>}
            </div>
        );
    } else {
        return (
            <>
                <h1>Es ist ein Fehler aufgetreten!</h1>
                {error.message && <p>Message: {error.message}</p>}
            </>
        );
    }
};

export default DefaultErrorPage;