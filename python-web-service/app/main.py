from flask import Flask, jsonify, request

app = Flask(__name__)

# Пример данных, которые могут быть использованы для поиска спортивного питания
products = [
    {"id": 1, "name": "Protein Powder", "category": "Protein", "price": 25.99},
    {"id": 2, "name": "Creatine", "category": "Supplements", "price": 15.49},
    {"id": 3, "name": "BCAA", "category": "Supplements", "price": 19.99}
]

# Главная страница
@app.route('/')
def home():
    return "Welcome to the Sports Nutrition API!"

# API для получения всех продуктов
@app.route('/products', methods=['GET'])
def get_products():
    return jsonify(products)

# API для получения информации о конкретном продукте по ID
@app.route('/products/<int:product_id>', methods=['GET'])
def get_product(product_id):
    product = next((item for item in products if item["id"] == product_id), None)
    if product:
        return jsonify(product)
    else:
        return jsonify({"message": "Product not found!"}), 404

# API для добавления нового продукта
@app.route('/products', methods=['POST'])
def add_product():
    data = request.get_json()
    new_product = {
        "id": len(products) + 1,
        "name": data["name"],
        "category": data["category"],
        "price": data["price"]
    }
    products.append(new_product)
    return jsonify(new_product), 201

# Запуск приложения
if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0', port=5000)
