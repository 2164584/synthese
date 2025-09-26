import React, {useEffect, useState} from 'react';
import CompItem from './CompItem';
import Item from '../model/Item';
import {axiosInstance} from "../App";
import Pagination from "./Pagination";
import { toast } from 'react-toastify';
import ProgressToast from './ProgressToast';


function ItemList({ itemList, getSuperCProducts, getMetroProducts, getIgaProducts, getMaxiProducts }) {
    const [currentPage, setCurrentPage] = useState(1);
    const [updateActivated, setUpdateActivated] = useState(true);
    const [onlyDiscount, setOnlyDiscount] = useState(false);
    const [nameValue, setNameValue] = useState('');
    const [filteredItems, setFilteredItems] = useState([]);

    const [checkboxes, setCheckboxes] = useState({
        SuperC: true,
        Maxi: true,
        IGA: true,
        Metro: true
    });
    const itemsPerPage = 100

    useEffect(() => {
        setCheckboxes(prevState => ({
            ...prevState,
            SuperC: true,
            Maxi: true,
            IGA: true,
            Metro: true
        }));
    }, []);

    useEffect(() => {
        const newFilteredItems = filterItems(itemList, onlyDiscount, checkboxes, nameValue);
        setFilteredItems(newFilteredItems);
    }, [nameValue, onlyDiscount, checkboxes, itemList]);

    const filterItems = (itemList, onlyDiscount, checkboxes, name) => {
        return itemList.filter(item => {
            const isManufacturerChecked = checkboxes[item.manufacturer];
            const isDiscounted = !onlyDiscount || item.isDiscountedThisWeek;
            const matchesName = name === '' || item.name.toLowerCase().includes(name.toLowerCase());
            return isManufacturerChecked && isDiscounted && matchesName;
        });
    };

    const indexOfLastItem = currentPage * itemsPerPage;
    const indexOfFirstItem = indexOfLastItem - itemsPerPage;
    const currentItems = filteredItems.slice(indexOfFirstItem, indexOfLastItem);
    const totalPages = Math.ceil(filteredItems.length / itemsPerPage);
    let toastId = null;

    const updateProducts = async (endpoint, getProduct, store) => {
        try {
            setUpdateActivated(false);
            pollProgress(getProduct, store);
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

    function showProgressToast(store) {
        if (!toast.isActive(toastId)) {
            toastId = toast.info(<ProgressToast store={store} percent={0} />, {
            position: "top-left",
            autoClose: false,
            closeOnClick: false,
            draggable: false,
            });
        }
    }

    function updateProgressToast(percent, store) {
        toast.update(toastId, {
            render: <ProgressToast store={store} percent={percent} />,
        });

        if (percent >= 100) {
            setTimeout(() => {
            toast.dismiss(toastId);
            }, 3000);
        }
    }

    const pollProgress = (getProduct, store) => {
        showProgressToast(store);

        const interval = setInterval(() => {
            axiosInstance.get('/products/' + store.toLowerCase() + '-progress')
            .then(res => {
                const percent = res.data;
                updateProgressToast(percent, store);

                if (percent >= 100) {
                    clearInterval(interval);
                }
            })
            .catch(err => {
                console.error("Error polling progress:", err);
                clearInterval(interval);
            });
        }, 250);
    };



    return (
        <div className='row'>
            <div className="col-12">
                <div className="row my-2">
                    <h2 className="col-12">Items</h2>
                    {['SuperC', 'Maxi', 'IGA', 'Metro'].map((store, index) => (
                        <div key={index} className="col-3 my-auto">
                            <label htmlFor={store}>{store}</label>
                            <input
                                id={store}
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
                                }, store)}
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
                            {onlyDiscount ? 'Tous les items' : 'Items en rabais'}
                        </button>
                    </div>
                </div>
                <div className="row">
                    <div className="col-6 mb-2">
                        <input
                            type="text"
                            className="form-control"
                            placeholder="Search by name"
                            value={nameValue}
                            onChange={(e) => setNameValue(e.target.value)}
                        />
                    </div>
                </div>
                <div className="row">
                    {currentItems.map((item, index) => (
                        <CompItem key={index} item={item}/>
                    ))}
                </div>
                <Pagination currentPage={currentPage} totalPages={totalPages} onPrevPage={handlePrevPage}
                            onNextPage={handleNextPage}/>
            </div>
        </div>
    );
}

export default ItemList;