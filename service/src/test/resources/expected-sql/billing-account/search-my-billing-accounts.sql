SELECT SKIP {offset} FIRST {limit}
      p.project_id AS id,
      p.name AS name,
      pt.payment_terms_id AS paymentTerms_id,
      pt.description AS paymentTerms_description,
      RTRIM(decode (p.active, 1, 'Active', 'Inactive')) AS status,
      p.sales_tax AS salesTax,
      p.po_box_number AS poNumber,
      p.start_date AS startDate,
      p.end_date AS endDate,
      p.budget AS budgetAmount,
      p.creation_date AS createdAt,
      p.creation_user AS createdBy,
      p.modification_date AS updatedAt,
      p.modification_user AS updatedBy,
      p.description AS description,
      p.subscription_number AS subscriptionNumber,
      p.company_id AS companyId,
      p.is_manual_prize_setting AS manualPrizeSetting,
      cp.client_id AS clientId
FROM  project p
LEFT OUTER JOIN payment_terms pt ON pt.payment_terms_id = p.payment_terms_id
LEFT OUTER JOIN client_project cp ON cp.project_id = p.project_id
JOIN project_manager pm ON pm.project_id = p.project_id
JOIN user_account ua ON ua.user_account_id = pm.user_account_id
JOIN common_oltp\:user co ON co.user_id = :loggedInUser AND co.handle = ua.user_name
AND {filter}
ORDER BY {order}