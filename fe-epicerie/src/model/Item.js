class CompItem{
    image
    name
    brand
    price
    gram
    pricePerHundGram
    discountPrice
    isDiscountedThisWeek
    isDiscountedNextWeek
    manufacturer

    init(data){
        this.image = data.image
        this.name = data.name
        this.brand = data.brand
        this.price = data.price
        this.gram = data.gram
        this.pricePerHundGram = data.pricePerHundGram
        this.discountPrice = data.discountPrice
        this.isDiscountedThisWeek = data.isDiscountedThisWeek
        this.isDiscountedNextWeek = data.isDiscountedNextWeek
        this.manufacturer = data.manufacturer
    }
    constructor() {}
}
export default CompItem;