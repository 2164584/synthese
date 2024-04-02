import Item from "./Item";

function ItemList({itemList}) {
  return (
      <div className='row'>
          <div className="col-12">
              <h3>Items</h3>
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