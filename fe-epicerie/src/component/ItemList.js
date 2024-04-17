import React, { useState } from 'react';
import Item from './Item';
import {axiosInstance} from "../App";
import Pagination from "./Pagination";

function ItemList({ itemList, getSuperCProducts, getMetroProducts, getIgaProducts, getMaxiProducts }) {
    const [currentPage, setCurrentPage] = useState(1);
    const [updateActivated, setUpdateActivated] = useState(true);
    const itemsPerPage = 100

    const indexOfLastItem = currentPage * itemsPerPage;
    const indexOfFirstItem = indexOfLastItem - itemsPerPage;
    const currentItems = itemList.slice(indexOfFirstItem, indexOfLastItem);

    const totalPages = Math.ceil(itemList.length / itemsPerPage);

    const updateSuperC = async () => {
        try {
            setUpdateActivated(false)
            await axiosInstance.post('/products/update-superc');
            getSuperCProducts();
            setUpdateActivated(true)
        } catch (error) {
            console.error('Error updating SuperC products:', error);
        }
    };

    const updateMetro = async () => {
        try {
            setUpdateActivated(false)
            await axiosInstance.post('/products/update-metro');
            getMetroProducts();
            setUpdateActivated(true)
        } catch (error) {
            console.error('Error updating Metro products:', error);
        }
    }

    const updateIga = async () => {
        try {
            setUpdateActivated(false)
            await axiosInstance.post('/products/update-iga');
            getIgaProducts();
            setUpdateActivated(true)
        } catch (error) {
            console.error('Error updating IGA products:', error);
        }
    }

    const updateMaxi = async () => {
        try {
            setUpdateActivated(false)
            await axiosInstance.post('/products/update-maxi');
            getMaxiProducts();
            setUpdateActivated(true)
        } catch (error) {
            console.error('Error updating Maxi products:', error);
        }
    }

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
                        <button className={`btn btn-primary ${updateActivated? '' : 'disabled'}`} onClick={updateSuperC}>Update</button>
                    </div>
                    <div className="col-2 my-auto">
                        <label htmlFor="maxi">Maxi</label>
                        <input type="checkbox" name="maxi" className="m-2"/>
                        <button className={`btn btn-primary ${updateActivated? '' : 'disabled'}`} onClick={updateMaxi}>Update</button>
                    </div>
                    <div className="col-2 my-auto">
                        <label htmlFor="iga">IGA</label>
                        <input type="checkbox" name="iga" className="m-2"/>
                        <button className={`btn btn-primary ${updateActivated? '' : 'disabled'}`} onClick={updateIga}>Update</button>
                    </div>
                    <div className="col-2 my-auto">
                        <label htmlFor="metro">Metro</label>
                        <input type="checkbox" name="metro" className="m-2"/>
                        <button className={`btn btn-primary ${updateActivated? '' : 'disabled'}`} onClick={updateMetro}>Update</button>
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
                <Pagination currentPage={currentPage} totalPages={totalPages} onPrevPage={handlePrevPage} onNextPage={handleNextPage} />
            </div>
        </div>
    );
}

export default ItemList;