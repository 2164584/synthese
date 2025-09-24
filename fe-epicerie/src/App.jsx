import './App.css';
import Item from "./model/Item";
import ItemList from "./component/ItemList";
import axios from "axios";
import {useEffect, useState} from "react";


function App() {
    const [scProducts, setScProducts] = useState([new Item()]);
    const [metroProducts, setMetroProducts] = useState([new Item()]);
    const [igaProducts, setIgaProducts] = useState([new Item()]);
    const [maxiProducts, setMaxiProducts] = useState([new Item()]);
    const [itemList, setItemList] = useState([new Item()]);

    function getSuperCProducts() {
         axiosInstance.get('/products/superc')
            .then(res => {
                setScProducts(res.data.map(item => {
                    let newItem = new Item()
                    newItem.init(item)
                    return newItem
                }))
            })
            .catch(err => {
                console.log(err)
            })
    }

    function getMetroProducts() {
        axiosInstance.get('/products/metro')
            .then(res => {
                setMetroProducts(res.data.map(item => {
                    let newItem = new Item()
                    newItem.init(item)
                    return newItem
                }))
            })
            .catch(err => {
                console.log(err)
            })
    }

    function getIgaProducts() {
        axiosInstance.get('/products/iga')
            .then(res => {
                setIgaProducts(res.data.map(item => {
                    let newItem = new Item()
                    newItem.init(item)
                    return newItem
                }))
            })
            .catch(err => {
                console.log(err)
            })
    }

    function getMaxiProducts() {
        axiosInstance.get('/products/maxi')
            .then(res => {
                setMaxiProducts(res.data.map(item => {
                    let newItem = new Item()
                    newItem.init(item)
                    return newItem
                }))
            })
            .catch(err => {
                console.log(err)
            })
    }

    function concatAllItems() {
        setItemList([...scProducts, ...metroProducts, ...igaProducts, ...maxiProducts])
    }

    useEffect(() => {
        concatAllItems()
    }, [scProducts, metroProducts, igaProducts, maxiProducts]);

    useEffect(() => {
        getSuperCProducts()
        getMetroProducts()
        getIgaProducts()
        getMaxiProducts()
    },[]);


  return (
    <div className="container-fluid bg-light">
      <div className='row'>
        <div className='col-12'>
          <h1 className='text-info text-center fw-light my-2'>
            Épicerie
          </h1>
        </div>
      </div>
        <div className="row">
            <div className="col-8 mx-auto">
                <ItemList itemList={itemList} getSuperCProducts={getSuperCProducts} getMetroProducts={getMetroProducts} getIgaProducts={getIgaProducts} getMaxiProducts={getMaxiProducts}/>
            </div>
        </div>
    </div>
  );
}

export default App;
export const baseURL = "http://localhost:8080"
export const axiosInstance = axios.create({
    baseURL: baseURL,
    headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json'
    },
    params: {}
})
