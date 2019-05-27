const mongoose = require("mongoose");
const express = require("express");
const Schema = mongoose.Schema;
const app = express();
const jsonParser = express.json();
const morgan = require("morgan")

const productSchema = new Schema({
    code: {
        type: Number,
        required: true
    },
    name: {
        type: String,
        required: true
    },
    calories: Number,
    proteins: Number,
    carbohydrates: Number,
    fats: Number,
    },
    { versionKey: false });
const Product = mongoose.model("Product", productSchema);

mongoose.connect("mongodb://localhost:27017/CPFC_DB", {useNewUrlParser: true}, function (err) {
    if (err) return console.log(err);
    app.listen(8080, function () {
        console.log("The server has been launched.");
    });
});

app.use(morgan("dev"));

app.get("/api/products", function (req, res) {
    Product.find({}, function (err, products) {
        if (err) return console.log(err);
        res.send(products);
    });
});

app.get("/api/products/:code", function (req, res) {
    const code = req.params.code;
    Product.findOne({code: code}, function (err, product) {
        if (err) return console.log(err);
        if (product) res.send(product);
        else res.status(404).send({name:"Not Found"});
    })
});

app.get("/api/products/:name", function (req, res) {
    const name = req.params.name;
    Product.findOne({name: name}, function (err, product) {
        if (err) return console.log(err);
        res.send(product);
    })
});

app.post("/api/products", jsonParser, function (req, res) {

    if (!req.body) return res.sendStatus(400);

    const name = req.body.name;
    const code = req.body.code;
    const calories = req.body.calories || 0;
    const fats = req.body.fats || 0;
    const carbohydrates = req.body.carbohydrates || 0;
    const proteins = req.body.proteins || 0;

    Product.create({
            name: name,
            code: code,
            calories: calories,
            fats: fats,
            carbohydrates: carbohydrates,
            proteins: proteins,
        },
        function (err, doc) {
            if (err) return console.log(err);
            res.send(doc);
        });
});

app.delete("/api/products/:code", function (req, res) {
    const code = req.params.code;
    Product.findOneAndDelete({code: code}, function (err, product) {
        if (err) return console.log(err);
        res.send(product);
    })
});

app.put("/api/products", jsonParser,async function (req, res) {
    if(!req.body) return res.sendStatus(400);
    const code = req.body.code;
    let calories = req.body.calories!==null ? req.body.calories : null;
    let fats = req.body.fats!==null ? req.body.fats : null;
    let carbohydrates = req.body.carbohydrates!==null ? req.body.carbohydrates : null;
    let proteins = req.body.proteins!==null?req.body.proteins: null;
    await Product.findOne({code:code}, function (err, product) {
        if (err) return console.log(err);
        if (!calories &&calories!==0) calories = product.calories;
        if (!fats && fats!==0) fats = product.fats;
        if (!carbohydrates &&carbohydrates!==0 ) carbohydrates = product.carbohydrates;
        if (!proteins &&proteins!==0) proteins = product.proteins;
    });
    const newProduct = {
        calories:calories,
        fats:fats,
        carbohydrates:carbohydrates,
        proteins:proteins
    };
    Product.findOneAndUpdate({code:code}, newProduct, {new:true}, function (err, product) {
        if(err) return console.log(err);
        res.send(product);
    })
});
