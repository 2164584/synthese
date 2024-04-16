function Item({item, index}) {
    return (
        <div key={index} className="col-3 mb-2">
            <div className="p-2 border border-2 rounded bg-white">
                <img src={item.image} alt={item.name} className="w-100"/>
                <div>
                    <div className="fs-5">{item.name}</div>
                    {
                        item.brand === "" ? <div className="fw-light fst-italic">Pas de marque</div> : <div className="fw-light fst-italic">{item.brand}</div>
                    }
                    <div className="row fw-light">
                        <div className="col-6">
                            {item.gram}
                        </div>
                        <div className="col-6 text-end">
                            {
                                item.isDiscountedThisWeek ?
                                    <div className="row">
                                        <span className="col-12 text-danger fw-bold">{item.discountPrice}</span>
                                        <span className="col-12 text-decoration-line-through">{item.price.replace("Prix régulier", "").trim()}</span>
                                    </div>
                                    :
                                    <div className="col-12">
                                        {item.price}
                                    </div>
                            }
                        </div>
                        <button className="mx-auto mt-2 col-10 btn btn-primary">Ajouter</button>

                    </div>
                </div>
            </div>
        </div>
    )
}

export default Item;