INSERT INTO categories (name, parent_id) VALUES
('Electronics', NULL),
('Clothing', NULL),
('Books', NULL),
('Smartphones', 1),
('Laptops', 1),
('Men', 2),
('Women', 2);

INSERT INTO products (name, description, price, stock, category_id, image_url) VALUES
('iPhone 15 Pro', 'Latest Apple smartphone with A17 Pro chip', 999.99, 50, 4, 'https://via.placeholder.com/300x300?text=iPhone+15'),
('Samsung Galaxy S24', 'Flagship Android phone with AI features', 899.99, 40, 4, 'https://via.placeholder.com/300x300?text=Galaxy+S24'),
('MacBook Pro 14', 'M3 Pro chip, 18GB RAM, 512GB SSD', 1999.99, 25, 5, 'https://via.placeholder.com/300x300?text=MacBook+Pro'),
('Dell XPS 15', 'Intel Core i7, 16GB RAM, OLED display', 1499.99, 30, 5, 'https://via.placeholder.com/300x300?text=Dell+XPS'),
('Classic Cotton T-Shirt', 'Comfortable 100% cotton t-shirt', 29.99, 200, 6, 'https://via.placeholder.com/300x300?text=T-Shirt'),
('Summer Dress', 'Lightweight floral summer dress', 59.99, 150, 7, 'https://via.placeholder.com/300x300?text=Dress'),
('Clean Code', 'A Handbook of Agile Software Craftsmanship by Robert Martin', 39.99, 100, 3, 'https://via.placeholder.com/300x300?text=Clean+Code'),
('Design Patterns', 'Elements of Reusable Object-Oriented Software', 49.99, 80, 3, 'https://via.placeholder.com/300x300?text=Design+Patterns');
