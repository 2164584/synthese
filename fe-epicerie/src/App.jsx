import './App.css';
import Item from "./component/Item";
import ItemList from "./component/ItemList";
import axios from "axios";
import Pomme from "./image/apple.jpg"
import Avocat from "./image/avocat.jpg"
import {useEffect} from "react";


function App() {

    useEffect(() => {
        axiosInstance.get('/products/superc')
            .then(res => {
                console.log(res.data)
            })
            .catch(err => {
                console.log(err)
            })
    }, []);


  let itemList = [
      {
          image: Pomme,
          name: 'Pommes',
          brand: 'Brand A',
          price: 10.99,
          gram: '100g',
          pricePerHundGram: 5.50,
          discountPrice: 9.99,
          isDiscountedThisWeek: true,
          isDiscountedNextWeek: false
      },
      {
          image: Avocat,
          name: 'Avocats',
          brand: 'Brand B',
          price: 5.99,
          gram: '150g',
          pricePerHundGram: 4.00,
          discountPrice: 4.49,
          isDiscountedThisWeek: false,
          isDiscountedNextWeek: true
      }
  ]


  return (
    <div className="container-fluid bg-light">
      <div className='row'>
        <div className='col-12'>
          <h1 className='text-info text-center fw-light'>
            Épicerie
          </h1>
        </div>
      </div>
        <div className="row">
            <div className="col-8 mx-auto">
                <ItemList itemList={itemList}/>
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
