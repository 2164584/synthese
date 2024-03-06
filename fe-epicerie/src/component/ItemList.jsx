function ItemList({itemList}) {
  return (
    <div className='row'>
      <div className="col-12">
        <h3>Items</h3>
        <div className="d-flex">

            {itemList.map((item, index) => (
                <div key={index} className="col-3 mx-3 border border-2 rounded">
                    <img src={item.image} alt={item.name} className="img-fluid"/>
                    <div>
                        <h3>{item.name}</h3>
                        <p>{item.brand}</p>
                        <p>{item.price}$</p>
                    </div>
                </div>
            ))}
        </div>
      </div>
    </div>
  );
}

export default ItemList;