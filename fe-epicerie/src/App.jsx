import './App.css';
import Item from "./model/Item";
import ItemList from "./component/ItemList";
import axios from "axios";
import {useEffect, useState} from "react";


function App() {
    const [itemList, setItemList] = useState([new Item()]);

    function getSuperCProducts() {
         axiosInstance.get('/products/superc')
            .then(res => {
                setItemList(res.data.map(item => {
                    let newItem = new Item()
                    newItem.init(item)
                    return newItem
                }))
            })
            .catch(err => {
                console.log(err)
            })
    }

    useEffect(() => {
        getSuperCProducts()
    }, []);


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
                <ItemList itemList={itemList} getSperCProducts={getSuperCProducts}/>
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
