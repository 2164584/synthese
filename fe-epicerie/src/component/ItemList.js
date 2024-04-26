import React, {useEffect, useState} from 'react';
import Item from './Item';
import {axiosInstance} from "../App";
import Pagination from "./Pagination";

function ItemList({ itemList, getSuperCProducts, getMetroProducts, getIgaProducts, getMaxiProducts }) {
    const [currentPage, setCurrentPage] = useState(1);
    const [updateActivated, setUpdateActivated] = useState(true);
    const [onlyDiscount, setOnlyDiscount] = useState(false);
    const [checkboxes, setCheckboxes] = useState({
        SuperC: true,
        Maxi: true,
        IGA: true,
        Metro: true
    });
    const itemsPerPage = 100
    useEffect(() => {
        // Update the state of checkboxes
        setCheckboxes(prevState => ({
            ...prevState,
            SuperC: true,
            Maxi: true,
            IGA: true,
            Metro: true
        }));
    }, []);

    const filterItems = (itemList, onlyDiscount, checkboxes) => {
        // Filter items based on the checked categories and manufacturer field
        return itemList.filter(item => {
            const isManufacturerChecked = checkboxes[item.manufacturer];
            const isDiscounted = !onlyDiscount || item.isDiscountedThisWeek;
            return isManufacturerChecked && isDiscounted;
        });
    };

    // Apply the filter to the item list based on the `onlyDiscount` state and the checkboxes state
    const filteredItems = filterItems(itemList, onlyDiscount, checkboxes);

    const indexOfLastItem = currentPage * itemsPerPage;
    const indexOfFirstItem = indexOfLastItem - itemsPerPage;
    const currentItems = filteredItems.slice(indexOfFirstItem, indexOfLastItem);

    const totalPages = Math.ceil(filteredItems.length / itemsPerPage);

    const updateProducts = async (endpoint, getProduct) => {
        try {
            setUpdateActivated(false);
            await axiosInstance.post(endpoint);
            getProduct();
            setUpdateActivated(true);
        } catch (error) {
            console.error(`Error updating products: ${error}`);
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
                <div className="row my-2">
                    <h2 className="col-2">Items</h2>
                    {['SuperC', 'Maxi', 'IGA', 'Metro'].map((store, index) => (
                        <div key={index} className="col-2 my-auto">
                            <label htmlFor={store}>{store}</label>
                            <input
                                type="checkbox"
                                name={store}
                                className="m-2"
                                checked={checkboxes[store]}
                                onChange={() => setCheckboxes(prevState => ({
                                    ...prevState,
                                    [store]: !prevState[store]
                                }))}
                            />
                            <button
                                className={`btn btn-primary ${updateActivated ? '' : 'disabled'}`}
                                onClick={() => updateProducts(`/products/update-${store.toLowerCase()}`, () => {
                                    console.log('store:', store);
                                    switch (store) {
                                        case 'SuperC':
                                            return getSuperCProducts();
                                        case 'Maxi':
                                            return getMaxiProducts();
                                        case 'IGA':
                                            return getIgaProducts();
                                        case 'Metro':
                                            return getMetroProducts();
                                        default:
                                            return;
                                    }
                                })}
                            >
                                Update
                            </button>
                        </div>
                    ))}
                </div>
                <div className="row my-2">
                    <div className="col-3">
                        <button
                            className={`btn btn-secondary ${onlyDiscount ? 'active' : ''}`}
                            onClick={() => setOnlyDiscount(!onlyDiscount)}
                        >
                            {onlyDiscount ? 'Show All Items' : 'Show Discounted Items'}
                        </button>
                    </div>
                </div>
                <div className="row">
                    {currentItems.map((item, index) => (
                        <Item key={index} item={item}/>
                    ))}
                </div>
                <Pagination currentPage={currentPage} totalPages={totalPages} onPrevPage={handlePrevPage}
                            onNextPage={handleNextPage}/>
            </div>
        </div>
    );
}

export default ItemList;