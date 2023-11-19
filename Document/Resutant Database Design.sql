CREATE database Restaurant;
USE Restaurant;
	
CREATE TABLE `User` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `role` NVARCHAR(255),
  `username` NVARCHAR(255),
  `userpass` NVARCHAR(255),
  `active` Boolean,
  `auth_disc` float
);

CREATE TABLE `Categories` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `name` NVARCHAR(255),
  `active` Boolean
);

CREATE TABLE `Product` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `name` nvarchar(255),
  `category_id` integer,
  `vatTax` float,
  `active` Boolean,
  `price` float,
  `type` int
);

CREATE TABLE `EnumType` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `name` NVARCHAR(255),
  `type` NVARCHAR(255)
);

CREATE TABLE `Feature` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `name` NVARCHAR(255),
  `price` integer
);
CREATE TABLE `LinkedProductFeature`(
	`id` integer PRIMARY KEY AUTO_INCREMENT,
    `productId` integer,
	`FeatureId` integer
);
CREATE TABLE `Orders` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `userId` integer,
  `comment` NVARCHAR(255),
  `disc` float
);

CREATE TABLE `OrderItems` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `orderID` integer,
  `productID` integer,
  `qty` integer,
  `disc` float
);

CREATE TABLE `OrderFeature` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `orderItemId` integer,
  `featureId` integer,
  `typeId` integer
);


ALTER TABLE `Product` ADD FOREIGN KEY (`category_id`) REFERENCES `Categories` (`id`);

ALTER TABLE `Product` ADD FOREIGN KEY (`type`) REFERENCES `EnumType` (`id`);

ALTER TABLE `Orders` ADD FOREIGN KEY (`userId`) REFERENCES `User` (`id`);

ALTER TABLE `OrderItems` ADD FOREIGN KEY (`productID`) REFERENCES `Product` (`id`);

ALTER TABLE `OrderItems` ADD FOREIGN KEY (`orderID`) REFERENCES `Orders` (`id`);

ALTER TABLE `OrderFeature` ADD FOREIGN KEY (`typeId`) REFERENCES `EnumType` (`id`);

ALTER TABLE `OrderFeature` ADD FOREIGN KEY (`featureId`) REFERENCES `Feature` (`id`);
ALTER TABLE `OrderFeature` ADD FOREIGN KEY (`orderItemId`) REFERENCES `OrderItems` (`id`);

ALTER TABLE `LinkedProductFeature` ADD foreign key (`productID`) References `Product`(`id`);

ALTER TABLE `LinkedProductFeature` ADD foreign key (`featureId`) References `Feature`(`id`);

INSERT INTO User (id, role, username, userpass, active, auth_disc)
VALUES
  (1, 'Master', 'Master', '12345', true, 100),
  (2, 'Waiter', 'Waiter', '12345', true, 5);

INSERT INTO Categories (id, name, active)
VALUES
  (1, 'Burger', true),
  (2, 'Pizza', true);

INSERT INTO EnumType (id, name, type)
VALUES
  (1, 'with', 'Feature'),
  (2, 'without', 'Feature'),
  (3, 'only With', 'Feature'),
  (4, 'Small Dose', 'Feature'),
  (5, 'Large Dose', 'Feature'),
  (6, 'Simple', 'Product'),
  (7, 'KG', 'Product'),
  (8, 'Free', 'Product');

INSERT INTO Product (id,name, category_id, vatTax, active, price, type)
VALUES
  (1,"Bazinga", 1, 0.1, true, 100.0, 7),
  (2,"Cheese", 2, 0.2, true, 200.0, 6),
  (3,"Zinger", 2, 0.5, true, 200.0, 8);

INSERT INTO Feature (id, name, price)
VALUES
  (1, 'Feature 1', 10),
  (2, 'Feature 2', 20);

INSERT INTO LinkedProductFeature (productId, FeatureId)
VALUES
  (1, 1),
  (1, 2),
  (2, 1);

INSERT INTO Orders (userId, comment, disc)
VALUES
  (1, 'Order comment 1', 0.05),
  (2, 'Order comment 2', 0.1);

INSERT INTO OrderItems (orderID, productID, qty, disc)
VALUES
  (1, 1, 2, 0.02),
  (1, 2, 1, 0.03);

INSERT INTO OrderFeature (orderItemId, featureId, typeId)
VALUES
  (1, 1, 1),
  (2, 2, 2);
