

BEGIN 
    FOR N IN 1..200 LOOP
    LOOP
    INSERT INTO NOTICE_TBL VALUES(SEQ_NOTICE_NO.NEXTVAL, '�������� '||N||'��°', N||'��° ���������Դϴ�', 'admin', DEFAULT, DEFAULT, NULL, NULL, NULL);
    END LOOP
END;
/

BEGIN
    FOR N IN 1..200
    LOOP
    INSERT INTO NOTICE_TBL VALUES(SEQ_NOTICE_NO.NEXTVAL, '�������� '||N||'��°', N||'��° ���������Դϴ�', 'admin', DEFAULT, DEFAULT, NULL, NULL, NULL);
    END LOOP;
END;
/

SELECT * FROM NOTICE_TBL WHERE NOTICE_SUBJECT LIKE '%����%';