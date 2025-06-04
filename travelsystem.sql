-- 数据库初始化脚本
-- 创建用户表
CREATE TABLE IF NOT EXISTS user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL,
    nickname VARCHAR(50),
    email VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 创建旅游线路表
CREATE TABLE IF NOT EXISTS tour_line (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    destination VARCHAR(100),
    price DECIMAL(10,2),
    duration INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status INT DEFAULT 0
);

-- 创建收藏表
CREATE TABLE IF NOT EXISTS favorite (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    tour_line_id INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_fav_user FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE,
    CONSTRAINT fk_fav_line FOREIGN KEY (tour_line_id) REFERENCES tour_line(id) ON DELETE CASCADE,
    UNIQUE KEY idx_user_line (user_id, tour_line_id)
);

-- 创建会员表
CREATE TABLE IF NOT EXISTS members (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    phone VARCHAR(20),
    status VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
