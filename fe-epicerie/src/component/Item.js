function Item({item, index}) {
    return (
        <div key={index} className="col-3 mb-2">
            <div className="p-2 border border-2 rounded">
                <img src={item.image} alt={item.name} className="w-100"/>
                <div>
                    <h3>{item.name}</h3>
                    <p>{item.brand}</p>
                    <p>{item.price}</p>
                </div>
            </div>
        </div>
    )
}

export default Item;