import Item from "./Item";

function ItemList({itemList}) {
  return (
      <div className='row'>
          <div className="col-12">
              <div className="row my-2">
                  <h2 className="col-2">Items</h2>
                  <div className="col-2 my-auto">
                      <label htmlFor="superc">Super C</label>
                      <input type="checkbox" name="superc" className="m-2"/>
                  </div>
                  <div className="col-2 my-auto">
                      <label htmlFor="superc">Maxi</label>
                      <input type="checkbox" name="maxi" className="m-2"/>
                  </div>
                  <div className="col-2 my-auto">
                      <label htmlFor="superc">IGA</label>
                      <input type="checkbox" name="iga" className="m-2"/>
                  </div>
                  <div className="col-2 my-auto">
                      <label htmlFor="superc">Metro</label>
                      <input type="checkbox" name="metro" className="m-2"/>
                  </div>
              </div>
              <div className="row my-2">
                    <div className="col-3">
                        <input type="text" className="form-control" placeholder="Rechercher"/>
                    </div>
                    <div className="col-3">
                        <button className="btn btn-primary">Rechercher</button>
                    </div>
              </div>
              <div className="row">
                  {itemList.slice(0, 100).map((item, index) =>
                      <Item index={index} item={item}/>
                  )}
              </div>
          </div>
      </div>
  );
}

export default ItemList;