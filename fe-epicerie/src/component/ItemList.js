import React, { useState } from 'react';
import Item from './Item';
import axios from "axios";
import {axiosInstance} from "../App";

function ItemList({ itemList, getSperCProducts }) {
    const [currentPage, setCurrentPage] = useState(1);
    const [updateSuperCActivated, setUpdateSuperCActivated] = useState(true);
    const itemsPerPage = 100

    const indexOfLastItem = currentPage * itemsPerPage;
    const indexOfFirstItem = indexOfLastItem - itemsPerPage;
    const currentItems = itemList.slice(indexOfFirstItem, indexOfLastItem);

    const totalPages = Math.ceil(itemList.length / itemsPerPage);

    const updateSuperC = async () => {
        try {
            setUpdateSuperCActivated(false)
            await axiosInstance.post('/products/update-superc');
            getSperCProducts();
            setUpdateSuperCActivated(true)
        } catch (error) {
            console.error('Error updating SuperC products:', error);
        }
    };

    const handleNextPage = () => {
        if (currentPage < totalPages) {
            setCurrentPage(currentPage + 1);
        }
    };

    const handlePrevPage = () => {
        if (currentPage > 1) {
            setCurrentPage(currentPage - 1);
        }
    };

    return (
        <div className='row'>
            <div className="col-12">
                {/* Header section */}
                <div className="row my-2">
                    <h2 className="col-2">Items</h2>
                    <div className="col-2 my-auto">
                        <label htmlFor="superc">Super C</label>
                        <input type="checkbox" name="superc" className="m-2"/>
                        <button className={`btn btn-primary ${updateSuperCActivated? '' : 'disabled'}`} onClick={updateSuperC}>Update</button>
                    </div>
                    <div className="col-2 my-auto">
                        <label htmlFor="maxi">Maxi</label>
                        <input type="checkbox" name="maxi" className="m-2"/>
                        <button className="btn btn-primary">Update</button>
                    </div>
                    <div className="col-2 my-auto">
                        <label htmlFor="iga">IGA</label>
                        <input type="checkbox" name="iga" className="m-2"/>
                        <button className="btn btn-primary">Update</button>
                    </div>
                    <div className="col-2 my-auto">
                        <label htmlFor="metro">Metro</label>
                        <input type="checkbox" name="metro" className="m-2"/>
                        <button className="btn btn-primary">Update</button>
                    </div>
                </div>
                {/* Search bar section */}
                <div className="row my-2">
                    <div className="col-3">
                        <input type="text" className="form-control" placeholder="Search"/>
                    </div>
                    <div className="col-3">
                        <button className="btn btn-primary">Search</button>
                    </div>
                </div>
                {/* Item list section */}
                <div className="row">
                    {currentItems.map((item, index) => (
                        <Item key={index} item={item} />
                    ))}
                </div>
                {/* Pagination controls */}
                <div className="row p-2 sticky-bottom bg-white border border-primary rounded-top">
                    <div className="col text-center">
                        <button
                            className="btn btn-success mx-2"
                            onClick={handlePrevPage}
                            disabled={currentPage === 1}
                        >
                            -
                        </button>
                        <span>Page {currentPage} of {totalPages}</span>
                        <button
                            className="btn btn-success mx-2"
                            onClick={handleNextPage}
                            disabled={currentPage === totalPages}
                        >
                            +
                        </button>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default ItemList;