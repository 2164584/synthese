import React from 'react';

function Pagination({ currentPage, totalPages, onPrevPage, onNextPage }) {
    return (
        <div className="row p-2 sticky-bottom bg-white border border-primary rounded-top">
            <div className="col text-center">
                <button
                    className="btn btn-success mx-2"
                    onClick={onPrevPage}
                    disabled={currentPage === 1}
                >
                    Previous
                </button>
                <span>Page {currentPage} of {totalPages}</span>
                <button
                    className="btn btn-success mx-2"
                    onClick={onNextPage}
                    disabled={currentPage === totalPages}
                >
                    Next
                </button>
            </div>
        </div>
    );
}

export default Pagination;
