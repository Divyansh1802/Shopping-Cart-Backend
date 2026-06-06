
# 🛒 Shopping Cart Backend (Spring Boot)

========================================
📌 PROJECT OVERVIEW
========================================

This is a Spring Boot-based Shopping Cart Backend application that provides REST APIs for an e-commerce system including authentication, product management, cart operations, and order processing.

The backend is deployed on Render and uses a Neon PostgreSQL database for persistence.

========================================
🚀 LIVE BACKEND URL
========================================

Base URL:
https://shopping-cart-backend-2ey0.onrender.com

Example API:
https://shopping-cart-backend-2ey0.onrender.com/Dream_shop/web/products

========================================
🗄️ DATABASE (NEON POSTGRESQL)
========================================

Database is hosted on Neon PostgreSQL cloud.

📊 Tables (Schemas):

- users
- my_role
- user_roles
- category
- product
- image
- cart
- cart_item
- orders
- order_item

These tables handle:
- Authentication & Authorization
- Product catalog
- Cart system
- Order processing
- Role-based access control

========================================
🧩 TECH STACK
========================================

- Java 17
- Spring Boot
- Spring Security
- JWT Authentication
- Spring Data JPA (Hibernate)
- PostgreSQL (Neon)
- Maven
- Render (Deployment platform)

========================================
🔐 AUTHENTICATION SYSTEM
========================================

This project uses JWT (JSON Web Token) based authentication.

Flow:
1. User logs in with credentials
2. Server generates JWT token
3. Token is sent in response
4. Client must send token in headers:

   Authorization: Bearer <your_token>

Protected endpoints will return 401 Unauthorized if token is missing or invalid.

========================================
🛍️ FEATURES
========================================

👤 USER MODULE:
- Register user
- Login user
- Role-based access (ADMIN / USER)

🛒 CART MODULE:
- Add products to cart
- Update quantity
- Remove item from cart
- View cart items

📦 ORDER MODULE:
- Place order from cart
- Fetch user orders
- Delete orders

📦 PRODUCT MODULE:
- Add product (Admin only)
- Update product
- Delete product
- Search product by:
  - ID
  - Name
  - Category
  - Brand

🏷️ CATEGORY MODULE:
- Add categories
- Assign products to categories

🖼️ IMAGE MODULE:
- Store product images linked to products

========================================
🌐 API BASE PATH
========================================

All APIs are prefixed with:

/Dream_shop/web

Example endpoints:

POST   /auth/login
POST   /auth/register
GET    /products
GET    /products/{id}
POST   /cart/add
GET    /cart
POST   /orders/place
GET    /orders/user

========================================
⚙️ LOCAL SETUP
========================================

1️⃣ Clone repository:
git clone https://github.com/Divyansh1802/Shopping-Cart-Backend.git

2️⃣ Move into project:
cd Shopping-Cart-Backend

3️⃣ Configure database in application.yml:

spring:
  datasource:
    url: jdbc:postgresql://<NEON_HOST>/<DB_NAME>
    username: <USERNAME>
    password: <PASSWORD>

4️⃣ Build project:
mvn clean install

5️⃣ Run project:
mvn spring-boot:run

========================================
☁️ DEPLOYMENT DETAILS
========================================

🚀 Backend Hosting:
- Platform: Render
- URL: https://shopping-cart-backend-2ey0.onrender.com
- Auto deploy enabled from GitHub

🗄️ Database Hosting:
- Platform: Neon PostgreSQL
- Cloud-managed PostgreSQL instance

========================================
⚠️ IMPORTANT NOTES
========================================

- Always pass JWT token for protected routes
- Render free tier may sleep after inactivity
- Ensure CORS is configured for frontend integration
- Use correct API prefix: /Dream_shop/web

========================================
👨‍💻 AUTHOR
========================================

Name: Divyansh Upadhyay
GitHub: https://github.com/Divyansh1802

========================================
📜 LICENSE
========================================

This project is open-source and free to use under MIT License.
EOF**
