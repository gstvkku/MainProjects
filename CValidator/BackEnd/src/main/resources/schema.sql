
-- =========================
-- USER
-- =========================
CREATE TABLE IF NOT EXISTS app_user (
    id UUID PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL
);

-- =========================
-- CV
-- =========================
CREATE TABLE IF NOT EXISTS cv (
    id UUID PRIMARY KEY,
    file_name VARCHAR(255) NOT NULL,
    file_path VARCHAR(500) NOT NULL,
    upload_date TIMESTAMP NOT NULL,
    status VARCHAR(20) NOT NULL,
    user_id UUID NOT NULL,

    CONSTRAINT fk_cv_user
        FOREIGN KEY (user_id)
        REFERENCES app_user(id)
        ON DELETE CASCADE
);

-- =========================
-- CV ANALYSIS
-- =========================
CREATE TABLE IF NOT EXISTS cv_analysis (
    id UUID PRIMARY KEY,
    score INT NOT NULL,
    strengths TEXT,
    weaknesses TEXT,
    suggestions TEXT,
    analyzed_at TIMESTAMP NOT NULL,
    cv_id UUID NOT NULL UNIQUE,

    CONSTRAINT fk_analysis_cv
        FOREIGN KEY (cv_id)
        REFERENCES cv(id)
        ON DELETE CASCADE
);

-- =========================
-- CV SKILLS
-- =========================
CREATE TABLE IF NOT EXISTS cv_skills (
    cv_id UUID NOT NULL,
    skill VARCHAR(100) NOT NULL,

    PRIMARY KEY (cv_id, skill),

    CONSTRAINT fk_skill_cv
        FOREIGN KEY (cv_id)
        REFERENCES cv(id)
        ON DELETE CASCADE
);